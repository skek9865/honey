package project.honey.business.controller.analyze;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import project.honey.business.dto.analaze.Dto040304;
import project.honey.business.form.analyze.Search040304;
import project.honey.business.service.analyze.Service040304;
import project.honey.business.service.basic.Service040103;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/040304")
public class Controller040304 {

    private final Service040304 service040304;
    private final Service040103 service040103;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search040304 search,
                          Model model, Pageable pageable){
        log.info("주문서현황 메인");
        log.info("menuId = {}", menuIdDto);
        log.info("search = {}", search);
        if(search.getSYmd1() == null || search.getSYmd2() == null){
            search.setYmd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "출하지시서번호", "품목", "규격", "수량",
                "출하창고", "거래처", "연락처", "적요"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        model.addAttribute("whouseCodes", service040103.findAllBySelect());

        Page<Dto040304> resultList = service040304.findAllByDsl(search, pageable);

        int qty = 0;
        for(Dto040304 dto : resultList) qty += dto.getQty();
        List<String> footer = GlobalMethod.makeFooter("", "", "", "", String.valueOf(qty), "", "", "", "");
        model.addAttribute("footers", footer);

        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "business/analyze/040304";
    }

    @GetMapping("/excel")
    public void excel(@ModelAttribute("search") Search040304 search,
                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("주문서현황 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "출하지시서번호", "품목", "규격", "수량",
                "출하창고", "거래처", "연락처", "적요"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "Tint",
                "String", "String", "String", "String"
        );

        List<List<String>> excelData = service040304.findAllByExcel(search);
        String fileName = "주문서현황(040304).xlsx";

        excelMaker.makeExcel("주문서현황 (040304)", titles, excelData, excelType, fileName, response);
    }
}
