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
import project.honey.business.form.manage.Search040204;
import project.honey.business.form.manage.Tb412Form;
import project.honey.business.form.manage.Tb413Form;
import project.honey.business.service.basic.Service040103;
import project.honey.business.service.basic.Service040109;
import project.honey.business.service.basic.Service040110;
import project.honey.business.service.manage.Service040202;
import project.honey.business.service.manage.Service040203;
import project.honey.business.service.manage.Service040204;
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
@RequestMapping("/040204")
public class Controller040204 {

    private final Service040204 service040204;
    private final Service020101 service020101;
    private final Service040103 service040103;
    private final Service040109 service040109;
    private final Service040203 service040203;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search040204 search,
                          Model model, Pageable pageable){
        log.info("출하지시서 메인");
        log.info("menuId = {}", menuIdDto);
        log.info("search = {}", search);
        if(search.getSYmd1() == null || search.getSYmd2() == null){
            search.setYmd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "출하지시서번호", "출하창고", "거래처명", "품목",
                "수량", "이름/연락처","배송우편번호", "배송주소", "배송주소상세", "기타사항", "인쇄하기"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        model.addAttribute("statusCodes",service990301.findByFstId("12"));
        model.addAttribute("whouseCodes",service040103.findAllBySelect());

        Page<Tb413MainDto> resultList = service040204.findAllByDsl(search, pageable);

        int qty = 0;
        for(Tb413MainDto dto : resultList) qty += dto.getQty();
        List<String> footer = GlobalMethod.makeFooter("", "", "", "", "", "", String.valueOf(qty), "", "", "", "", "", "");
        model.addAttribute("footers", footer);

        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "business/040204";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("출하지시서 input");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));

        List<String> titles = GlobalMethod.makeTitle("삭제", "코드", "품목", "규격", "수량", "제조일자", "적요");

        GlobalConst globalConst = new GlobalConst();

        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", globalConst);
        model.addAttribute("titles",titles);

        model.addAttribute("empCodes",service020101.findAllBySelect());
        model.addAttribute("whouseCodes",service040103.findAllBySelect());
        model.addAttribute("statusCodes",service990301.findByFstId("12"));
        model.addAttribute("projectCodes",service040109.findAllBySelect());

        Long shipNo = service040204.findShipNo();

        List<Tb413_1Dto> subDtos = new ArrayList<>();

        if(map.get("vseq").isEmpty()){
            for(int i = 0; i < globalConst.getSubInputIdx(); i++) subDtos.add(new Tb413_1Dto(0));
            model.addAttribute("dto",new Tb413Dto(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), shipNo.intValue()));
            model.addAttribute("subDtos",subDtos);
            return "business/040204_input";
        }
        Tb413Dto dto = service040204.findById(Integer.parseInt(map.get("vseq")));
        List<Tb413_1Dto> findTb413_1 = service040204.findChildByFk(dto.getSeq());

        int qtyT = 0;
        for(Tb413_1Dto subDto : findTb413_1) qtyT += subDto.getQty();
        model.addAttribute("qtyT", qtyT);

        if(findTb413_1.size() <= 5){
            for(int i = 0; i < globalConst.getSubInputIdx(); i++) {
                if(findTb413_1.size() - 1 < i) subDtos.add(new Tb413_1Dto(0));
                else subDtos.add(findTb413_1.get(i));
            }
        }
        else{
            for(int i = findTb413_1.size() - 1; i < globalConst.getSubInputIdx(); i++) subDtos.add(findTb413_1.get(i));
        }
        model.addAttribute("dto", dto);
        model.addAttribute("subDtos", subDtos);
        return "business/040204_input";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb413Form tb413Form,
                         Model model, HttpServletRequest request){
        log.info("출하지시서 insert");
        log.info("tb413Form = {}", tb413Form);
        log.info("tb413_1Forms = {}", tb413Form.getTb413_1Forms());
        if (service040204.insert(tb413Form, tb413Form.getTb413_1Forms())) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb413Form tb413Form,
                         Model model, HttpServletRequest request){
        log.info("출하지시서 update");
        log.info("tb413Form = {}", tb413Form);
        log.info("tb413_1Forms = {}", tb413Form.getTb413_1Forms());
        if(service040204.update(tb413Form, tb413Form.getTb413_1Forms())) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        log.info("출하지시서 delete");
        if(service040204.delete(id)) model.addAttribute("msg", "정상적으로 삭제 되었습니다.");
        else model.addAttribute("msg", "문제가 발생하였습니다");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/excel")
    public void excel(@ModelAttribute("search") Search040204 search,
                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("출하지시서 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "출하지시서번호", "출하창고", "거래처명", "품목", "수량",
                "이름/연락처","배송우편번호", "배송주소", "배송주소상세", "기타사항", "인쇄하기"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String", "Tint",
                "String", "String", "String", "String", "String", "String"
        );

        List<List<String>> excelData = service040204.findAllByExcel(search);
        String fileName = "출하지시서(040204).xlsx";

        excelMaker.makeExcel("출하지시서 (040204)", titles, excelData, excelType, fileName, response);
    }

    @GetMapping("/salePopUp")
    public String salePopUp(@ModelAttribute("search") SearchPopUp410 search, Model model){
        log.info("판매검색 PopUp");
        model.addAttribute("global", new GlobalConst());

        if(search.getSYmd1() == null || search.getSYmd2() == null){
            search.setYmd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        List<String> titles = GlobalMethod.makeTitle("순번", "판매번호", "거래처명", "담당자", "품목", "수량", "선택");
        model.addAttribute("titles", titles);

        List<Tb412MainDto> result = service040203.findAllByPopUp(search);
        model.addAttribute("dtos", result);
        return "business/040204_1";
    }

    @GetMapping("/popInput")
    public String findByPopUp(@RequestParam Map<String, String> map, Model model){
        log.info("출하지시서 popInput");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));

        List<String> titles = GlobalMethod.makeTitle("삭제", "코드", "품목", "규격", "수량", "제조일자", "적요");

        GlobalConst globalConst = new GlobalConst();

        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", globalConst);
        model.addAttribute("titles",titles);

        model.addAttribute("empCodes",service020101.findAllBySelect());
        model.addAttribute("whouseCodes",service040103.findAllBySelect());
        model.addAttribute("statusCodes",service990301.findByFstId("12"));
        model.addAttribute("projectCodes",service040109.findAllBySelect());

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        model.addAttribute("date", date);

        Long shipNo = service040204.findShipNo();
        model.addAttribute("shipNo", shipNo.intValue());

        List<Tb412_1Dto> subPopDtos = new ArrayList<>();
        Tb412Dto dto = service040203.findById(Integer.parseInt(map.get("vseq")));
        List<Tb412_1Dto> findTb412_1 = service040203.findChildByFk(dto.getSeq());

        int qtyT = 0;
        for(Tb412_1Dto subDto : findTb412_1) qtyT += subDto.getQty();
        model.addAttribute("qtyT", qtyT);

        if(findTb412_1.size() <= 5){
            for(int i = 0; i < globalConst.getSubInputIdx(); i++) {
                if(findTb412_1.size() - 1 < i) subPopDtos.add(new Tb412_1Dto(0));
                else subPopDtos.add(findTb412_1.get(i));
            }
        }
        else{
            for(int i = findTb412_1.size() - 1; i < globalConst.getSubInputIdx(); i++) subPopDtos.add(findTb412_1.get(i));
        }
        model.addAttribute("dto", dto);
        model.addAttribute("subDtos", subPopDtos);
        return "business/040204_popInput";
    }

}
