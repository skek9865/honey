package project.honey.stock.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.honey.business.service.basic.Service040103;
import project.honey.business.service.basic.Service040109;
import project.honey.comm.*;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.stock.dto.Tb601Dto;
import project.honey.stock.dto.Tb601_1Dto;
import project.honey.stock.dto.Tb602Dto;
import project.honey.stock.dto.Tb602_1Dto;
import project.honey.stock.dto.form.Tb601Form;
import project.honey.stock.dto.form.Tb602Form;
import project.honey.stock.dto.search.Search060101;
import project.honey.stock.dto.search.Search060102;
import project.honey.stock.service.Service060101;
import project.honey.stock.service.Service060102;
import project.honey.system.service.Service990301;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/060102")
public class Controller060102 {

    private final Service040103 service040103;
    private final Service040109 service040109;
    private final Service060102 service060102;
    private final Service990301 service990301;
    private final CodeToName codeToName;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search060102 search, Model model, Pageable pageable) {

        // 날짜 기본값 세팅
        if(search.getYmd1() == null){
            search.setYmd1(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            search.setYmd2(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "입고순번","거래처명", "창고","품목", "수량", "담당자", "적요", "상태"
        );
        model.addAttribute("menus", menuMaker.getMenuId(1, "", "", ""));
        model.addAttribute("menuNm", menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles", titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb602Dto> result = service060102.findAll(search, pageable);
        List<Tb602Dto> content = result.getContent();
        // 푸터
        int qty = 0;
        for (Tb602Dto dto : content) {
            qty += dto.getQty() != null ? dto.getQty() : 0;
        }
        List<String> footers = GlobalMethod.makeFooter(
                "", "", "", "", "","", String.valueOf(qty), "","","");

        model.addAttribute("footers", footers);
        model.addAttribute("statuses", service990301.findByFstId("12"));
        model.addAttribute("pageMaker", new PageMaker(pageable, result.getTotalElements()));
        model.addAttribute("dtos", content);

        return "stock/060102";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb602Form dto, Model model, HttpServletRequest request) {
        log.info("dto : {}", dto);
        model.addAttribute("msg", service060102.insert(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb602Form dto, Model model, HttpServletRequest request) {
        log.info("dto : {}", dto);
        model.addAttribute("msg", service060102.update(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{seq}")
    public String delete(@PathVariable Integer seq, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service060102.delete(seq) != null ? "정상적으로 삭제 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/input")
    public String input(Model model,String action, Integer seq){

        log.info("input");
        log.info("seq = {}", seq);

        List<String> titles = GlobalMethod.makeTitle(
                "삭제","코드", "품목","수량","사용유형", "적요"
        );

        model.addAttribute("statuses", service990301.findByFstId("12"));
        model.addAttribute("projects", service040109.findAllBySelect());
        model.addAttribute("wHouses", service040103.findAllBySelect());

        // 퇴사한 인원들 안나와서 임시로 codeToName 사용용
//       model.addAttribute("emps", service020101.findAllBySelect());
        model.addAttribute("emps", codeToName.emp());

        model.addAttribute("action", action);
        model.addAttribute("titles", titles);
        model.addAttribute("global", new GlobalConst());

        Tb602Form form = service060102.findById(seq);
        List<Tb602_1Dto> dtoList = form.getTb602_1Dtos();
        int qty = 0;
        for (Tb602_1Dto dto : dtoList) {
            qty += dto != null ? dto.getQty() : 0;
        }
        model.addAttribute("dto", form);
        model.addAttribute("qty_tot", qty);

        return "stock/060102_input";
    }

    @GetMapping("/excel")
    public void excel(Search060102 search, HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("자가사용관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "입고순번","거래처명", "창고","품목", "수량", "담당자", "적요", "상태"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String", "int", "String", "String", "String"
        );

        List<List<String>> excelData = service060102.findAllByExcel(search);
        String fileName = "자가사용관리(060102).xls";

        excelMaker.makeExcel("자가사용관리 (060102)", titles, excelData, excelType, fileName, response);
    }
}
