package project.honey.produce.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.honey.comm.*;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.personDepart.service.Service020101;
import project.honey.produce.dto.Tb504Dto;
import project.honey.produce.dto.Tb504_1Dto;
import project.honey.produce.dto.form.Tb504Form;
import project.honey.produce.dto.search.Search050201;
import project.honey.produce.service.Service050101;
import project.honey.produce.service.Service050201;
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
@RequestMapping("/050201")
public class Controller050201 {

    private final Service020101 service020101;
    private final Service050101 service050101;
    private final Service050201 service050201;
    private final Service990301 service990301;
    private final CodeToName codeToName;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search050201 search, Model model, Pageable pageable) {

        // 날짜 기본값 세팅
        if(search.getYmd1() == null){
            search.setYmd1(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            search.setYmd2(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "작업지시순번", "담당자","품목", "납기일자",
                "목표수량", "생산수량", "상태"
        );
        model.addAttribute("menus", menuMaker.getMenuId(1, "", "", ""));
        model.addAttribute("menuNm", menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles", titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb504Dto> result = service050201.findAll(search, pageable);
        List<Tb504Dto> content = result.getContent();
        // 푸터
        int qty = 0;
        int rQty = 0;
        for (Tb504Dto dto : content) {
            qty += dto.getQty() != null ? dto.getQty() : 0;
            rQty += dto.getRQty() != null ? dto.getRQty() : 0;
        }
        List<String> footers = GlobalMethod.makeFooter(
                "", "", "", "", "","", String.valueOf(qty),String.valueOf(rQty), "");

        model.addAttribute("footers", footers);
        model.addAttribute("statuses", service990301.findByFstId("18"));
        model.addAttribute("pageMaker", new PageMaker(pageable, result.getTotalElements()));
        model.addAttribute("dtos", content);

        return "produce/050201";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb504Form dto, Model model, HttpServletRequest request) {
        log.info("dto : {}", dto);
        model.addAttribute("msg", service050201.insert(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb504Form dto, Model model, HttpServletRequest request) {
        log.info("dto : {}", dto);
        model.addAttribute("msg", service050201.update(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{seq}")
    public String delete(@PathVariable Integer seq, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service050201.delete(seq) != null ? "정상적으로 삭제 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }
    @GetMapping("/input")
    public String input(Model model,String action, Integer seq){

        log.info("input");
        log.info("seq = {}", seq);

        List<String> titles = GlobalMethod.makeTitle(
                "삭제","코드", "품목명", "규격","목표수량","생산수량", "상태", "호기","작업자",
                "적요"
        );

        model.addAttribute("statuses", service990301.findByFstId("18"));
        model.addAttribute("machines", service990301.findByFstId("15"));

        // 퇴사한 인원들 안나와서 임시로 codeToName 사용용
//       model.addAttribute("emps", service020101.findAllBySelect());
        model.addAttribute("emps", codeToName.emp());
        model.addAttribute("products", service050101.findAllBySelect());

        model.addAttribute("action", action);
        model.addAttribute("titles", titles);
        model.addAttribute("global", new GlobalConst());

        Tb504Form form = service050201.findById(seq);
        List<Tb504_1Dto> dtoList = form.getTb504_1Dtos();
        int qty = 0;
        int rQty = 0;
        for (Tb504_1Dto dto : dtoList) {
            qty += dto != null ? dto.getQty() : 0;
            rQty += dto != null ? dto.getRQty() : 0;
        }
        model.addAttribute("dto", form);
        model.addAttribute("qty_tot", qty);
        model.addAttribute("rqty_tot", rQty);

        return "produce/050201_input";
    }

    @GetMapping("/excel")
    public void excel(Search050201 search, HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("작업지시서관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "작업지시순번", "담당자", "품목", "납기일자", "목표수량", "생산수량", "상태"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String", "int", "int", "String"
        );

        List<List<String>> excelData = service050201.findAllByExcel(search);
        String fileName = "작업지시서관리(050201).xls";

        excelMaker.makeExcel("작업지시서관리 (050201)", titles, excelData, excelType, fileName, response);
    }
}
