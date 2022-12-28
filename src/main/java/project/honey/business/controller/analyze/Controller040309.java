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
import project.honey.business.dto.analaze.Dto040309;
import project.honey.business.form.analyze.Search040309;
import project.honey.business.form.analyze.VatType;
import project.honey.business.service.analyze.Service040309;
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
@RequestMapping("/040309")
public class Controller040309 {

    private final Service040309 service040309;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @ModelAttribute("vatTypes")
    public VatType[] vatTypes(){
        return VatType.values();
    }

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search040309 search,
                          Model model, Pageable pageable){
        log.info("매출청구서현황 메인");
        log.info("menuId = {}", menuIdDto);
        log.info("search = {}", search);
        if(search.getSYmd1() == null || search.getSYmd2() == null){
            search.setYmd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "판매번호", "거래처", "공급가액", "부가세", "합계"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Dto040309> resultList = service040309.findAllByDsl(search, pageable);

        int amt = 0, vat = 0, tot = 0;
        for(Dto040309 dto : resultList) {
            amt += dto.getAmt();
            vat += dto.getVat();
            tot += dto.getTot();
        }
        List<String> footer = GlobalMethod.makeFooter("", "", "", String.valueOf(amt), String.valueOf(vat), String.valueOf(tot));
        model.addAttribute("footers", footer);

        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "business/analyze/040309";
    }

    @GetMapping("/excel")
    public void excel(@ModelAttribute("search") Search040309 search,
                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("매출청구서현황 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "판매번호", "거래처", "공급가액", "부가세", "합계"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "Tint", "Tint", "Tint"
        );

        List<List<String>> excelData = service040309.findAllByExcel(search);
        String fileName = "매출청구서현황(040309).xlsx";

        excelMaker.makeExcel("매출청구서현황 (040309)", titles, excelData, excelType, fileName, response);
    }
}
