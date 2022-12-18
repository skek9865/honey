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
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.stock.dto.Dto060109;
import project.honey.stock.dto.Dto060111;
import project.honey.stock.dto.search.Search060109;
import project.honey.stock.dto.search.Search060111;
import project.honey.stock.service.Service060109;
import project.honey.stock.service.Service060111;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/060111")
public class Controller060111 {

    private final Service060111 service060111;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search060111 search, Model model, Pageable pageable) {

        // 날짜 기본값 세팅
        if(search.getYmd1() == null){
            search.setYmd1(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            search.setYmd2(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "처리순번", "품목", "창고","재고수량","실사수량","조정수량","금액", "적요"
        );
        model.addAttribute("menus", menuMaker.getMenuId(1, "", "", ""));
        model.addAttribute("menuNm", menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles", titles);
        model.addAttribute("global", new GlobalConst());

        Page<Dto060111> result = service060111.findAll(search, pageable);
        List<Dto060111> content = result.getContent();
        // 푸터
        int stQty = 0;
        int reQty = 0;
        int adQty = 0;
        int wPrice = 0;
        for (Dto060111 dto : content) {
            stQty += dto.getStQty() != null ? dto.getStQty() : 0;
            reQty += dto.getReQty() != null ? dto.getReQty() : 0;
            adQty += dto.getAdQty() != null ? dto.getAdQty() : 0;
            wPrice += dto.getWPrice() != null ? dto.getWPrice() : 0;
        }
        List<String> footers = GlobalMethod.makeFooter(
                "","", "","", String.valueOf(stQty),String.valueOf(reQty),
                String.valueOf(adQty),String.valueOf(wPrice),"");

        model.addAttribute("footers", footers);
        model.addAttribute("pageMaker", new PageMaker(pageable, result.getTotalElements()));
        model.addAttribute("dtos", content);

        return "stock/060111";
    }

    @GetMapping("/excel")
    public void excel(Search060111 search, HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("재고실사조정현황 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "처리순번", "품목", "창고","재고수량","실사수량","조정수량","금액", "적요"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String","String", "int", "int","int", "int", "String"
        );

        List<List<String>> excelData = service060111.findAllByExcel(search);
        String fileName = "재고실사조정현황(060111).xls";

        excelMaker.makeExcel("재고실사조정현황 (060111)", titles, excelData, excelType, fileName, response);
    }
}
