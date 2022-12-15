package project.honey.produce.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import project.honey.business.dto.search.Search405;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.produce.dto.Dto050104;
import project.honey.produce.dto.Dto050303;
import project.honey.produce.dto.Dto050304;
import project.honey.produce.dto.search.Search050302;
import project.honey.produce.service.Service050303;
import project.honey.produce.service.Service050304;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/050304")
public class Controller050304 {

    private final Service050304 service050304;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model, Pageable pageable) {

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "생산품목명", "소모품목명", "생산수량","표준소모수량", "실제소모수량",
                "소모품목단가", "금액차이"
        );
        model.addAttribute("menus", menuMaker.getMenuId(1, "", "", ""));
        model.addAttribute("menuNm", menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles", titles);
        model.addAttribute("global", new GlobalConst());

        Page<Dto050304> result = service050304.findAll(pageable);
        List<Dto050304> content = result.getContent();
        // 푸터 계산
        int pQty = 0;
        int sQty = 0;
        int cQty = 0;
        int cPrice = 0;
        int dPrice = 0;
        for (Dto050304 dto : content) {
            pQty += dto.getPQty() != null ? dto.getPQty() : 0;
            sQty += dto.getSQty() != null ? dto.getSQty() : 0;
            cQty += dto.getCQty() != null ? dto.getCQty() : 0;
            cPrice += dto.getCPrice() != null ? dto.getCPrice() : 0;
            dPrice += dto.getDPrice() != null ? dto.getDPrice() : 0;
        }
        // 푸터
        List<String> footers = GlobalMethod.makeFooter(
                "", "", "", String.valueOf(pQty), String.valueOf(sQty), String.valueOf(cQty),
                String.valueOf(cPrice), String.valueOf(dPrice));

        model.addAttribute("footers", footers);
        model.addAttribute("pageMaker", new PageMaker(pageable, result.getTotalElements()));
        model.addAttribute("dtos", content);

        return "produce/050304";
    }

    @GetMapping("/excel")
    public void excel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("생산입고,소모현황 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "생산품목명", "소모품목명", "생산수량","표준소모수량", "실제소모수량",
                "소모품목단가", "금액차이"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "int","int","int","int","int"
        );

        List<List<String>> excelData = service050304.findAllByExcel();
        String fileName = "생산입고,소모현황(050304).xls";

        excelMaker.makeExcel("생산입고,소모현황 (050304)", titles, excelData, excelType, fileName, response);
    }

}
