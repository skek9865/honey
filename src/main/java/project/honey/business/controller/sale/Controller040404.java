package project.honey.business.controller.sale;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import project.honey.business.dto.sale.Dto040404;
import project.honey.business.form.sale.Search040404;
import project.honey.business.service.sale.Service040404;
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
@RequestMapping("/040404")
public class Controller040404 {
    
    private final Service040404 service040404;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search040404 search,
                          Model model, Pageable pageable){
        log.info("지급현황 메인");
        log.info("menuId = {}", menuIdDto);
        log.info("search = {}", search);
        if(search.getSYmd1() == null || search.getSYmd2() == null){
            search.setYmd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "지급일자", "거래처", "급액", "적요"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Dto040404> resultList = service040404.findAllByDsl(search, pageable);

        int price = 0;
        for(Dto040404 dto : resultList) price += dto.getPrice();

        List<String> footer = GlobalMethod.makeFooter("", "", "", String.valueOf(price), "");
        model.addAttribute("footers", footer);

        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "business/sale/040404";
    }

    @GetMapping("/excel")
    public void excel(@ModelAttribute("search") Search040404 search,
                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("지급현황 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "지급일자", "거래처", "급액", "적요"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "Tint", "String"
        );

        List<List<String>> excelData = service040404.findAllByExcel(search);
        String fileName = "지급현황(040404).xlsx";

        excelMaker.makeExcel("지급현황 (040404)", titles, excelData, excelType, fileName, response);
    }
}
