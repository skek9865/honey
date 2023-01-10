package project.honey.business.controller.sale;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import project.honey.business.dto.sale.Dto040403;
import project.honey.business.form.analyze.Search040307;
import project.honey.business.service.basic.Service040101;
import project.honey.business.service.sale.Service040403;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.personDepart.service.Service020101;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/040403")
public class Controller040403 {

    private final Service040403 service040403;
    private final Service020101 service020101;
    private final Service040101 service040101;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search040307 search,
                          Model model){
        log.info("거래처별채무 메인");
        log.info("menuId = {}", menuIdDto);
        log.info("search = {}", search);
        if(search.getSYmd1() == null || search.getSYmd2() == null){
            search.setYmd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "담당자", "거래처", "매입액", "지급액", "잔액"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("empCodes", service020101.findAllBySelect());
        model.addAttribute("custGrCodes", service040101.findAllBySelect(1));

        List<Dto040403> resultList = service040403.findAllByDsl(search);

        int getPrice = 0, outPrice = 0, totPrice = 0;
        for(Dto040403 dto : resultList) {
            if(dto.getCustNm().equals("담당자 소계")) continue;
            getPrice += dto.getGetPrice();
            outPrice += dto.getOutPrice();
            totPrice += dto.getTotPrice();
        }
        List<String> footer = GlobalMethod.makeFooter("", "", "", String.valueOf(getPrice), String.valueOf(outPrice), String.valueOf(totPrice));
        model.addAttribute("footers", footer);

        model.addAttribute("dtos", resultList);

        return "business/sale/040403";
    }

    @GetMapping("/excel")
    public void excel(@ModelAttribute("search") Search040307 search,
                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("거래처별채무 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "담당자", "거래처", "매입액", "지급액", "잔액"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "Tint", "Tint", "Tint"
        );

        List<List<String>> excelData = service040403.findAllByExcel(search);
        String fileName = "거래처별채무(040403).xlsx";

        excelMaker.makeExcel("거래처별채무 (040403)", titles, excelData, excelType, fileName, response);
    }
}
