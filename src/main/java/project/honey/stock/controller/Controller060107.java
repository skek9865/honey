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
import project.honey.business.dto.search.Search405;
import project.honey.business.service.basic.Service040105;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.produce.dto.Dto050104;
import project.honey.produce.service.Service050104;
import project.honey.stock.dto.Dto060107;
import project.honey.stock.dto.search.Search060107;
import project.honey.stock.service.Service060107;
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
@RequestMapping("/060107")
public class Controller060107 {

    private final Service040105 service040105;
    private final Service060107 service060107;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search060107 search, Model model, Pageable pageable) {

        // 날짜 기본값 세팅
        if(search.getYmd1() == null){
            search.setYmd1(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            search.setYmd2(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "처리순번","처리방법","품목","수량","대체품목","정상수량","적요"
        );
        model.addAttribute("menus", menuMaker.getMenuId(1, "", "", ""));
        model.addAttribute("menuNm", menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles", titles);
        model.addAttribute("global", new GlobalConst());

        Page<Dto060107> result = service060107.findAll(search, pageable);
        List<Dto060107> content = result.getContent();
        int qty = 0;
        int rQty = 0;
        for (Dto060107 dto : content) {
            qty += dto.getQty() != null ? dto.getQty() : 0;
            rQty += dto.getRQty() != null ? dto.getRQty() : 0;
        }
        // 푸터
        List<String> footers = GlobalMethod.makeFooter(
                "", "", "", "", String.valueOf(qty),
                "",String.valueOf(rQty), "");

        model.addAttribute("footers", footers);
        model.addAttribute("pageMaker", new PageMaker(pageable, result.getTotalElements()));
        model.addAttribute("dtos", content);

        return "stock/060107";
    }

    @GetMapping("/excel")
    public void excel(Search060107 search, HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("대체사용현황 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "처리순번","처리방법","품목","수량","대체품목","정상수량","적요"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String",  "int",
                "String", "int","String"
        );

        List<List<String>> excelData = service060107.findAllByExcel(search);
        String fileName = "대체사용현황(060107).xls";

        excelMaker.makeExcel("대체사용현황 (060107)", titles, excelData, excelType, fileName, response);
    }
}
