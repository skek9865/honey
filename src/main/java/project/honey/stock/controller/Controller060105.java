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
import project.honey.stock.dto.Dto060104;
import project.honey.stock.dto.Dto060105;
import project.honey.stock.dto.search.Search060101;
import project.honey.stock.dto.search.Search060102;
import project.honey.stock.service.Service060104;
import project.honey.stock.service.Service060105;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/060105")
public class Controller060105 {

    private final Service060105 service060105;
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
                "순번", "입고순번", "거래처명", "품목","창고", "수량", "금액", "적요"
        );
        model.addAttribute("menus", menuMaker.getMenuId(1, "", "", ""));
        model.addAttribute("menuNm", menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles", titles);
        model.addAttribute("global", new GlobalConst());

        Page<Dto060105> result = service060105.findAll(search, pageable);
        List<Dto060105> content = result.getContent();
        // 푸터
        int qty = 0;
        int wPrice = 0;
        for (Dto060105 dto : content) {
            qty += dto.getQty() != null ? dto.getQty() : 0;
            wPrice += dto.getWPrice() != null ? dto.getWPrice() : 0;
        }
        List<String> footers = GlobalMethod.makeFooter(
                "", "", "", "","", String.valueOf(qty),String.valueOf(wPrice), "");

        model.addAttribute("footers", footers);
        model.addAttribute("pageMaker", new PageMaker(pageable, result.getTotalElements()));
        model.addAttribute("dtos", content);

        return "stock/060105";
    }

    @GetMapping("/excel")
    public void excel(Search060102 search, HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("자가사용현황 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "입고순번", "거래처명", "품목","창고", "수량", "금액", "적요"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String", "int","int", "String"
        );

        List<List<String>> excelData = service060105.findAllByExcel(search);
        String fileName = "자가사용현황(060105).xls";

        excelMaker.makeExcel("자가사용현황 (060105)", titles, excelData, excelType, fileName, response);
    }
}
