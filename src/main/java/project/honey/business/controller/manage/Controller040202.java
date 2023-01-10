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
import project.honey.business.form.manage.Search040201;
import project.honey.business.form.manage.Tb410Form;
import project.honey.business.form.manage.Tb411Form;
import project.honey.business.service.basic.Service040103;
import project.honey.business.service.basic.Service040109;
import project.honey.business.service.basic.Service040110;
import project.honey.business.service.manage.Service040201;
import project.honey.business.service.manage.Service040202;
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
@RequestMapping("/040202")
public class Controller040202 {

    private final Service040202 service040202;
    private final Service020101 service020101;
    private final Service040103 service040103;
    private final Service040109 service040109;
    private final Service040110 service040110;
    private final Service040201 service040201;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search040201 search,
                          Model model, Pageable pageable){
        log.info("주문서관리 메인");
        log.info("menuId = {}", menuIdDto);
        log.info("search = {}", search);
        if(search.getSYmd1() == null || search.getSYmd2() == null){
            search.setYmd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "주문번호", "거래처명", "담당자",
                "품목", "금액", "수량", "상태", "인쇄하기", "참조"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        model.addAttribute("empCodes",service020101.findAllBySelect());
        model.addAttribute("statusCodes",service990301.findByFstId("12"));

        Page<Tb411MainDto> resultList = service040202.findAllByDsl(search, pageable);

        int price = 0, qty = 0;
        for(Tb411MainDto dto : resultList) {
            price += dto.getPrice();
            qty += dto.getQty();
        }
        List<String> footer = GlobalMethod.makeFooter("", "", "", "", "", "", String.valueOf(price), String.valueOf(qty), "", "","");
        model.addAttribute("footers", footer);

        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "business/040202";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("주문서관리 input");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));

        List<String> titles = GlobalMethod.makeTitle(
                "삭제", "코드", "품목", "규격", "수량",
                "단가", "공급가액", "부가세", "납기일자", "적요"
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

        Long orderNo = service040202.findOrderNo();

        List<Tb411_1Dto> subDtos = new ArrayList<>();

        if(map.get("vseq").isEmpty()){
            for(int i = 0; i < globalConst.getSubInputIdx(); i++) subDtos.add(new Tb411_1Dto(0));
            model.addAttribute("dto",new Tb411Dto(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), orderNo.intValue()));
            model.addAttribute("subDtos",subDtos);
            return "business/040202_input";
        }
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
                if(findTb411_1.size() - 1 < i) subDtos.add(new Tb411_1Dto(0));
                else subDtos.add(findTb411_1.get(i));
            }
        }
        else{
            for(int i = findTb411_1.size() - 1; i < globalConst.getSubInputIdx(); i++) subDtos.add(findTb411_1.get(i));
        }
        model.addAttribute("dto", dto);
        model.addAttribute("subDtos", subDtos);
        return "business/040202_input";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb411Form tb411Form,
                         Model model, HttpServletRequest request){
        log.info("주문서관리 insert");
        log.info("tb411Form= {}", tb411Form);
        log.info("tb411_1Forms = {}", tb411Form.getTb411_1Forms());
        if (service040202.insert(tb411Form, tb411Form.getTb411_1Forms())) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb411Form tb411Form,
                         Model model, HttpServletRequest request){
        log.info("주문서관리 update");
        log.info("tb411Form = {}", tb411Form);
        log.info("tb411_1Forms = {}", tb411Form.getTb411_1Forms());
        if(service040202.update(tb411Form, tb411Form.getTb411_1Forms())) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        log.info("주문서관리 delete");
        if(service040202.delete(id)) model.addAttribute("msg", "정상적으로 삭제 되었습니다.");
        else model.addAttribute("msg", "문제가 발생하였습니다");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/excel")
    public void excel(@ModelAttribute("search") Search040201 search,
                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("주문서관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "주문번호", "거래처명", "담당자", "품목",
                "금액", "수량", "상태", "인쇄하기", "참조"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String",
                "Tint", "Tint", "String", "String", "String"
        );

        List<List<String>> excelData = service040202.findAllByExcel(search);
        String fileName = "주문서관리(040202).xlsx";

        excelMaker.makeExcel("주문서관리 (040202)", titles, excelData, excelType, fileName, response);
    }

    @GetMapping("/popup/{seq}")
    public String popUp(@PathVariable("seq") Integer seq, Model model){
        log.info("주문서관리 print");

        PrintData040202 printData = service040202.findPrintData(seq);

        int total = 0;
        List<PrintData040202_1> tb411_1Dtos = printData.getTb411_1Dtos();
        for(PrintData040202_1 pd : tb411_1Dtos){
            if(pd.getAmt() != null && pd.getVat() != null){
                total += (pd.getAmt() + pd.getVat());
            }
        }
        model.addAttribute("total", total);

        model.addAttribute("global", new GlobalConst());
        model.addAttribute("printData", printData);
        model.addAttribute("subPrintData", printData.getTb411_1Dtos());

        return "business/040202_prt";
    }

    @GetMapping("/estPopUp")
    public String estPopUp(@ModelAttribute("search") SearchPopUp410 search, Model model){
        log.info("견적서검색 PopUp");
        model.addAttribute("global", new GlobalConst());

        if(search.getSYmd1() == null || search.getSYmd2() == null){
            search.setYmd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        List<String> titles = GlobalMethod.makeTitle("순번", "견적번호", "거래처명", "담당자", "품목", "금액", "선택");
        model.addAttribute("titles", titles);

        List<Tb410MainDto> result = service040201.findAllByPopUp(search);
        model.addAttribute("dtos", result);
        return "business/040202_1";
    }

    @GetMapping("/popInput")
    public String findByPopUp(@RequestParam Map<String, String> map, Model model){
        log.info("주문서관리 popInput");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));

        List<String> titles = GlobalMethod.makeTitle(
                "삭제", "코드", "품목", "규격", "수량",
                "단가", "공급가액", "부가세", "납기일자", "적요"
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

        Long orderNo = service040202.findOrderNo();
        model.addAttribute("orderNo", orderNo);

        List<Tb410_1Dto> subPopDtos = new ArrayList<>();
        Tb410Dto dto = service040201.findById(Integer.parseInt(map.get("vseq")));
        List<Tb410_1Dto> findTb410_1 = service040201.findChildByFk(dto.getSeqP());

        int qtyT = 0, amtT = 0, vatT = 0, totT = 0;
        for(Tb410_1Dto subDto : findTb410_1){
            qtyT += subDto.getQty();
            amtT += subDto.getAmt();
            vatT += subDto.getVat();
        }
        totT += (amtT + vatT);
        model.addAttribute("qtyT", qtyT);
        model.addAttribute("amtT", amtT);
        model.addAttribute("vatT", vatT);
        model.addAttribute("totT", totT);

        if(findTb410_1.size() <= 5){
            for(int i = 0; i < globalConst.getSubInputIdx(); i++) {
                if(findTb410_1.size() - 1 < i) subPopDtos.add(new Tb410_1Dto(0));
                else subPopDtos.add(findTb410_1.get(i));
            }
        }
        else{
            for(int i = findTb410_1.size() - 1; i < globalConst.getSubInputIdx(); i++) subPopDtos.add(findTb410_1.get(i));
        }
        model.addAttribute("dto", dto);
        model.addAttribute("subDtos", subPopDtos);
        return "business/040202_popInput";
    }

}
