package project.honey.stock.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import project.honey.business.service.basic.Service040104;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.stock.dto.Dto060111;
import project.honey.stock.dto.Dto060201;
import project.honey.stock.dto.search.Search060111;
import project.honey.stock.dto.search.Search060201;
import project.honey.stock.service.Service060111;
import project.honey.stock.service.Service060201;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/060201")
public class Controller060201 {

    private final Service040104 service040104;
    private final Service060201 service060201;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search060201 search, Model model, Pageable pageable) {

        if (search.getClassSeq().size() != 6) {
            List<String> list = search.getClassSeq();
            while (list.size() < 6) {
                list.add(null);
                search.setClassSeq(list);
            }
        }

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "품목구분", "품목", "규격명","재고수량"
        );
        model.addAttribute("menus", menuMaker.getMenuId(1, "", "", ""));
        model.addAttribute("menuNm", menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles", titles);
        model.addAttribute("global", new GlobalConst());

        Page<Dto060201> result = service060201.findAll(search, pageable);
        List<Dto060201> content = result.getContent();

        // 셀렉트박스
        model.addAttribute("itemGbs", service040104.findAllBySelect());

        // 푸터
        int qty = content.stream().mapToInt(Dto060201::getQty).sum();

        List<String> footers = GlobalMethod.makeFooter(
                "","", "","", String.valueOf(qty));

        model.addAttribute("footers", footers);
        model.addAttribute("pageMaker", new PageMaker(pageable, result.getTotalElements()));
        model.addAttribute("dtos", content);

        return "stock/060201";
    }

    @GetMapping("/excel")
    public void excel(Search060201 search, HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("재고현황 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "품목구분", "품목", "규격명","재고수량"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String","String", "int"
        );

        List<List<String>> excelData = service060201.findAllByExcel(search);
        String fileName = "재고현황(060201).xls";

        excelMaker.makeExcel("재고현황 (060201)", titles, excelData, excelType, fileName, response);
    }
}
