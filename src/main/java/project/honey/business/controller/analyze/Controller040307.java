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
import project.honey.business.dto.analaze.Dto040306;
import project.honey.business.dto.analaze.Dto040307;
import project.honey.business.form.analyze.Search040306;
import project.honey.business.form.analyze.Search040307;
import project.honey.business.service.analyze.Service040307;
import project.honey.business.service.basic.Service040101;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
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
@RequestMapping("/040307")
public class Controller040307 {

    private final Service040307 service040307;
    private final Service020101 service020101;
    private final Service040101 service040101;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search040307 search,
                          Model model){
        log.info("거래처별채권 메인");
        log.info("menuId = {}", menuIdDto);
        log.info("search = {}", search);
        if(search.getSYmd1() == null || search.getSYmd2() == null){
            search.setYmd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "담당자", "거래처코드", "거래처", "매출액",
                "수금액", "잔액"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("empCodes", service020101.findAllBySelect());
        model.addAttribute("custGrCodes", service040101.findAllBySelect(1));

        List<Dto040307> resultList = service040307.findAllByDsl(search);

        int salePrice = 0, getPrice = 0, totPrice = 0;
        for(Dto040307 dto : resultList) {
            if(dto.getCustNm().equals("담당자 소계")) continue;
            salePrice += dto.getSalePrice();
            getPrice += dto.getGetPrice();
            totPrice += dto.getTotPrice();
        }
        List<String> footer = GlobalMethod.makeFooter("", "", "", "", String.valueOf(salePrice), String.valueOf(getPrice), String.valueOf(totPrice));
        model.addAttribute("footers", footer);

        model.addAttribute("dtos", resultList);

        return "business/analyze/040307";
    }

    @GetMapping("/excel")
    public void excel(@ModelAttribute("search") Search040307 search,
                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("거래처별채권 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "담당자", "거래처코드", "거래처", "매출액",
                "수금액", "잔액"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "Tint", "Tint", "Tint"
        );

        List<List<String>> excelData = service040307.findAllByExcel(search);
        String fileName = "거래처별채권(040307).xlsx";

        excelMaker.makeExcel("거래처별채권 (040307)", titles, excelData, excelType, fileName, response);
    }
}
