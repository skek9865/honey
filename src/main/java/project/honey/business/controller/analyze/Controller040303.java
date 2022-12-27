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
import project.honey.business.dto.analaze.Dto040303;
import project.honey.business.form.analyze.Search040302;
import project.honey.business.service.analyze.Service040303;
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
@RequestMapping("/040303")
public class Controller040303 {

    private final Service040303 service040303;
    private final Service020101 service020101;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search040302 search,
                          Model model, Pageable pageable){
        log.info("주문서현황 메인");
        log.info("menuId = {}", menuIdDto);
        log.info("search = {}", search);
        if(search.getSYmd1() == null || search.getSYmd2() == null){
            search.setYmd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "견적번호", "거래처", "담당자", "유효기간", "결제조건", "참조",
                "상태", "품목", "규격", "수량", "단가", "공급가액", "부가세", "납품일자"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        model.addAttribute("empCodes",service020101.findAllBySelect());

        Page<Dto040303> resultList = service040303.findAllByDsl(search, pageable);

        int price = 0, qty = 0, amt = 0, vat = 0;
        for(Dto040303 dto : resultList) {
            price += dto.getPrice();
            qty += dto.getQty();
            amt += dto.getAmt();
            vat += dto.getVat();
        }
        List<String> footer = GlobalMethod.makeFooter("", "", "", "", "", "", "", "", "", "", String.valueOf(qty), String.valueOf(price), String.valueOf(amt), String.valueOf(vat), "");
        model.addAttribute("footers", footer);

        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "business/analyze/040303";
    }

    @GetMapping("/excel")
    public void excel(@ModelAttribute("search") Search040302 search,
                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("주문서현황 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "견적번호", "거래처", "담당자", "유효기간", "결제조건", "참조",
                "상태", "품목", "규격", "수량", "단가", "공급가액", "부가세", "납품일자"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String", "String", "String",
                "String", "String", "String",  "Tint", "Tint", "Tint", "Tint", "String"
        );

        List<List<String>> excelData = service040303.findAllByExcel(search);
        String fileName = "주문서현황(040303).xlsx";

        excelMaker.makeExcel("주문서현황 (040303)", titles, excelData, excelType, fileName, response);
    }
}
