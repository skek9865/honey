package project.honey.business.controller.analyze;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import project.honey.business.dto.analaze.Dto040306;
import project.honey.business.dto.analaze.PrintData040306;
import project.honey.business.dto.analaze.PrintData040306_1;
import project.honey.business.form.analyze.Search040306;
import project.honey.business.service.analyze.Service040306;
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
@RequestMapping("/040306")
public class Controller040306 {

    private final Service040306 service040306;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search040306 search,
                          Model model, Pageable pageable){
        log.info("거래명세서인쇄 메인");
        log.info("menuId = {}", menuIdDto);
        log.info("search = {}", search);
        if(search.getSYmd1() == null || search.getSYmd2() == null){
            search.setYmd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "일자-No", "품목", "수량", "금액",
                "부가세", "합계", "인쇄"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Dto040306> resultList = service040306.findAllByDsl(search, pageable);

        int qty = 0, amt = 0, vat = 0, tot = 0;
        for(Dto040306 dto : resultList) {
            qty += dto.getQty();
            amt += dto.getAmt();
            vat += dto.getVat();
            tot += dto.getTot();
        }
        List<String> footer = GlobalMethod.makeFooter("", "", "", String.valueOf(qty), String.valueOf(amt), String.valueOf(vat), String.valueOf(tot), "");
        model.addAttribute("footers", footer);

        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "business/analyze/040306";
    }

    @GetMapping("/excel")
    public void excel(@ModelAttribute("search") Search040306 search,
                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("거래명세서인쇄 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "일자-No", "품목", "수량", "금액", "부가세", "합계"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "Tint", "Tint", "Tint", "Tint"
        );

        List<List<String>> excelData = service040306.findAllByExcel(search);
        String fileName = "거래명세서인쇄(040306).xlsx";

        excelMaker.makeExcel("거래명세서인쇄 (040306)", titles, excelData, excelType, fileName, response);
    }

    @GetMapping("/popup/{seq}")
    public String popUp(@PathVariable("seq") Integer seq, Model model){
        log.info("거래명세서인쇄 print");

        PrintData040306 printData = service040306.findPrintData(seq);

        int total = 0;
        List<PrintData040306_1> tb412_1Dtos = printData.getTb412_1Dtos();
        for(PrintData040306_1 pd : tb412_1Dtos){
            if(pd.getAmt() != null && pd.getVat() != null){
                total += (pd.getAmt() + pd.getVat());
            }
        }
        model.addAttribute("total", total);

        model.addAttribute("global", new GlobalConst());
        model.addAttribute("printData", printData);
        model.addAttribute("subPrintData", printData.getTb412_1Dtos());

        return "business/analyze/040306_prt";
    }
}
