package project.honey.business.controller.manage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.honey.business.dto.manage.*;
import project.honey.business.dto.search.SearchPopUp410;
import project.honey.business.form.manage.Search040203;
import project.honey.business.form.manage.Tb412Form;
import project.honey.business.service.basic.Service040103;
import project.honey.business.service.basic.Service040109;
import project.honey.business.service.basic.Service040110;
import project.honey.business.service.manage.Service040201;
import project.honey.business.service.manage.Service040202;
import project.honey.business.service.manage.Service040203;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.personDepart.service.Service020101;
import project.honey.system.service.Service990301;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/040203")
public class Controller040203 {

    private final Service040203 service040203;
    private final Service020101 service020101;
    private final Service040103 service040103;
    private final Service040109 service040109;
    private final Service040110 service040110;
    private final Service040202 service040202;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search040203 search,
                          Model model, Pageable pageable){
        log.info("판매관리 메인");
        log.info("menuId = {}", menuIdDto);
        log.info("search = {}", search);
        if(search.getSYmd1() == null || search.getSYmd2() == null){
            search.setYmd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "판매번호", "거래처명", "담당자",
                "품목", "금액", "수량", "인수증확인", "프로젝트", "기타사항",
                "이름/연락처", "배송우편번호", "배송주소", "배송주소상세",
                "출고시참조","상태", "인쇄하기"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        model.addAttribute("empCodes",service020101.findAllBySelect());
        model.addAttribute("projectCodes", service040109.findAllBySelect());
        model.addAttribute("statusCodes",service990301.findByFstId("12"));
        model.addAttribute("whouseCodes",service040103.findAllBySelect());

        Page<Tb412MainDto> resultList = service040203.findAllByDsl(search, pageable);

        int price = 0, qty = 0;
        for(Tb412MainDto dto : resultList) {
            price += dto.getPrice();
            qty += dto.getQty();
        }
        List<String> footer = GlobalMethod.makeFooter("", "", "", "", "", "", String.valueOf(price), String.valueOf(qty),
                "", "", "", "", "", "", "", "", "", "");
        model.addAttribute("footers", footer);

        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "business/040203";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("판매관리 input");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));

        List<String> titles = GlobalMethod.makeTitle(
                "삭제", "코드", "품목", "규격", "수량",
                "단가", "공급가액", "부가세", "합계", "적요", "단가(vat 포함)"
        );

        GlobalConst globalConst = new GlobalConst();

        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", globalConst);
        model.addAttribute("titles",titles);

        model.addAttribute("empCodes",service020101.findAllBySelect());
        model.addAttribute("saleTypeCodes",service990301.findByFstId("11"));
        model.addAttribute("whouseCodes",service040103.findAllBySelect());
        model.addAttribute("excgCodes",service040110.findAllBySelect());
        model.addAttribute("statusCodes",service990301.findByFstId("12"));
        model.addAttribute("projectCodes",service040109.findAllBySelect());

        Long saleNo = service040203.findSaleNo();

        List<Tb412_1Dto> subDtos = new ArrayList<>();

        if(map.get("vseq").isEmpty()){
            for(int i = 0; i < globalConst.getSubInputIdx(); i++) subDtos.add(new Tb412_1Dto(0));
            model.addAttribute("dto",new Tb412Dto(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), saleNo.intValue()));
            model.addAttribute("subDtos",subDtos);
            return "business/040203_input";
        }
        Tb412Dto dto = service040203.findById(Integer.parseInt(map.get("vseq")));
        List<Tb412_1Dto> findTb412_1 = service040203.findChildByFk(dto.getSeq());

        int qtyT = 0, amtT = 0, vatT = 0, totT = 0;
        for(Tb412_1Dto subDto : findTb412_1){
            qtyT += subDto.getQty();
            amtT += subDto.getAmt();
            vatT += subDto.getVat();
        }
        totT += (amtT + vatT);
        model.addAttribute("qtyT", qtyT);
        model.addAttribute("amtT", amtT);
        model.addAttribute("vatT", vatT);
        model.addAttribute("totT", totT);

        if(findTb412_1.size() <= 5){
            for(int i = 0; i < globalConst.getSubInputIdx(); i++) {
                if(findTb412_1.size() - 1 < i) subDtos.add(new Tb412_1Dto(0));
                else subDtos.add(findTb412_1.get(i));
            }
        }
        else{
            for(int i = findTb412_1.size() - 1; i < globalConst.getSubInputIdx(); i++) subDtos.add(findTb412_1.get(i));
        }
        model.addAttribute("dto", dto);
        model.addAttribute("subDtos", subDtos);
        return "business/040203_input";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb412Form tb412Form,
                         Model model, HttpServletRequest request){
        log.info("판매관리 insert");
        log.info("tb412Form= {}", tb412Form);
        log.info("tb412_1Forms = {}", tb412Form.getTb412_1Forms());
        if (service040203.insert(tb412Form, tb412Form.getTb412_1Forms())) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb412Form tb412Form,
                         Model model, HttpServletRequest request){
        log.info("판매관리 update");
        log.info("tb412Form = {}", tb412Form);
        log.info("tb412_1Forms = {}", tb412Form.getTb412_1Forms());
        if(service040203.update(tb412Form, tb412Form.getTb412_1Forms())) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        log.info("판매관리 delete");
        if(service040203.delete(id)) model.addAttribute("msg", "정상적으로 삭제 되었습니다.");
        else model.addAttribute("msg", "문제가 발생하였습니다");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/excel")
    public void excel(@ModelAttribute("search") Search040203 search,
                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("판매관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "판매번호", "거래처명", "담당자", "품목",
                "금액", "수량", "인수증확인", "프로젝트", "기타사항",
                "이름/연락처", "배송우편번호", "배송주소", "배송주소상세",
                "출고시참조","상태", "인쇄하기"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String",
                "Tint", "Tint", "String", "String", "String",
                "String", "String", "String", "String",
                "String", "String", "String"
        );

        List<List<String>> excelData = service040203.findAllByExcel(search);
        String fileName = "판매관리(040203).xlsx";

        excelMaker.makeExcel("판매관리 (040203)", titles, excelData, excelType, fileName, response);
    }

    @GetMapping("/orderPopUp")
    public String orderPopUp(@ModelAttribute("search") SearchPopUp410 search, Model model){
        log.info("주문서검색 PopUp");
        model.addAttribute("global", new GlobalConst());

        if(search.getSYmd1() == null || search.getSYmd2() == null){
            search.setYmd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        List<String> titles = GlobalMethod.makeTitle("순번", "견적번호", "거래처명", "담당자", "품목", "금액", "선택");
        model.addAttribute("titles", titles);

        List<Tb411MainDto> result = service040202.findAllByPopUp(search);
        model.addAttribute("dtos", result);
        return "business/040203_1";
    }

    @GetMapping("/popInput")
    public String findByPopUp(@RequestParam Map<String, String> map, Model model){
        log.info("판매관리 popInput");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));

        List<String> titles = GlobalMethod.makeTitle(
                "삭제", "코드", "품목", "규격", "수량",
                "단가", "공급가액", "부가세", "합계", "적요", "단가(vat 포함)"
        );

        GlobalConst globalConst = new GlobalConst();

        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", globalConst);
        model.addAttribute("titles",titles);

        model.addAttribute("empCodes",service020101.findAllBySelect());
        model.addAttribute("saleTypeCodes",service990301.findByFstId("11"));
        model.addAttribute("whouseCodes",service040103.findAllBySelect());
        model.addAttribute("excgCodes",service040110.findAllBySelect());
        model.addAttribute("statusCodes",service990301.findByFstId("12"));
        model.addAttribute("projectCodes",service040109.findAllBySelect());

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        model.addAttribute("date", date);

        Long saleNo = service040203.findSaleNo();
        model.addAttribute("saleNo", saleNo.intValue());

        List<Tb411_1Dto> subPopDtos = new ArrayList<>();
        Tb411Dto dto = service040202.findById(Integer.parseInt(map.get("vseq")));
        List<Tb411_1Dto> findTb411_1 = service040202.findChildByFk(dto.getSeq());

        int qtyT = 0, amtT = 0, vatT = 0, totT = 0;
        for(Tb411_1Dto subDto : findTb411_1){
            qtyT += subDto.getQty();
            amtT += subDto.getAmt();
            vatT += subDto.getVat();
        }
        totT += (amtT + vatT);
        model.addAttribute("qtyT", qtyT);
        model.addAttribute("amtT", amtT);
        model.addAttribute("vatT", vatT);
        model.addAttribute("totT", totT);

        if(findTb411_1.size() <= 5){
            for(int i = 0; i < globalConst.getSubInputIdx(); i++) {
                if(findTb411_1.size() - 1 < i) subPopDtos.add(new Tb411_1Dto(0));
                else subPopDtos.add(findTb411_1.get(i));
            }
        }
        else{
            for(int i = findTb411_1.size() - 1; i < globalConst.getSubInputIdx(); i++) subPopDtos.add(findTb411_1.get(i));
        }
        model.addAttribute("dto", dto);
        model.addAttribute("subDtos", subPopDtos);
        return "business/040203_popInput";
    }

}
