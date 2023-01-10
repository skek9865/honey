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
import project.honey.personDepart.service.Service020101;
import project.honey.produce.dto.Tb501Dto;
import project.honey.stock.dto.Tb601Dto;
import project.honey.stock.dto.Tb601_1Dto;
import project.honey.stock.dto.Tb604Dto;
import project.honey.stock.dto.form.Tb601Form;
import project.honey.stock.dto.form.Tb604Form;
import project.honey.stock.dto.search.Search060101;
import project.honey.stock.dto.search.Search060110;
import project.honey.stock.service.Service060101;
import project.honey.stock.service.Service060110;
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
@RequestMapping("/060110")
public class Controller060110 {

    private final Service020101 service020101;
    private final Service040103 service040103;
    private final Service060110 service060110;
    private final Service990301 service990301;
    private final CodeToName codeToName;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search060110 search, Model model, Pageable pageable) {

        // 날짜 기본값 세팅
        if(search.getYmd1() == null){
            search.setYmd1(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            search.setYmd2(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "처리순번", "담당자", "창고","품목", "재고수량","실사수량","조정수량", "적요"
        );
        model.addAttribute("menus", menuMaker.getMenuId(1, "", "", ""));
        model.addAttribute("menuNm", menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles", titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb604Dto> result = service060110.findAll(search, pageable);
        List<Tb604Dto> content = result.getContent();
        // 푸터
        int stQty = 0;
        int reQty = 0;
        int adQty = 0;
        for (Tb604Dto dto : content) {
            stQty += dto.getStQty() != null ? dto.getStQty() : 0;
            reQty += dto.getReQty() != null ? dto.getReQty() : 0;
            adQty += dto.getAdQty() != null ? dto.getAdQty() : 0;
        }
        List<String> footers = GlobalMethod.makeFooter(
                "", "", "", "", "","", String.valueOf(stQty), String.valueOf(reQty), String.valueOf(adQty), "");

        model.addAttribute("footers", footers);
        model.addAttribute("pageMaker", new PageMaker(pageable, result.getTotalElements()));
        model.addAttribute("dtos", content);

        return "stock/060110";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb604Form dto, Model model, HttpServletRequest request) {
        log.info("dto : {}", dto);
        model.addAttribute("msg", service060110.insert(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb604Form dto, Model model, HttpServletRequest request) {
        log.info("dto : {}", dto);
        model.addAttribute("msg", service060110.update(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{seq}")
    public String delete(@PathVariable Integer seq, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service060110.delete(seq) != null ? "정상적으로 삭제 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/input")
    public String input(Model model,String action, Integer seq){
        log.info("input");
        log.info("seq = {}", seq);

        //셀렉트박스
        model.addAttribute("emps", service020101.findAllBySelect());
        model.addAttribute("wHouses", service040103.findAllBySelect());

        model.addAttribute("action", action);
        model.addAttribute("dto",service060110.findById(seq));
        model.addAttribute("global", new GlobalConst());

        return "stock/060110_input";
    }

    @GetMapping("/excel")
    public void excel(Search060110 search, HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("재고실사조정관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "처리순번", "담당자", "창고","품목", "재고수량","실사수량","조정수량", "적요"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String", "int","int","int", "String"
        );

        List<List<String>> excelData = service060110.findAllByExcel(search);
        String fileName = "재고실사조정관리(060110).xls";

        excelMaker.makeExcel("재고실사조정관리 (060110)", titles, excelData, excelType, fileName, response);
    }
}
