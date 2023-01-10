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
import project.honey.business.dto.analaze.Dto040301;
import project.honey.business.form.analyze.Search040301;
import project.honey.business.form.manage.Search040203;
import project.honey.business.service.analyze.Service040301;
import project.honey.business.service.basic.Service040101;
import project.honey.business.service.basic.Service040109;
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
@RequestMapping("/040301")
public class Controller040301 {

    private final Service040301 service040301;
    private final Service040101 service040101;
    private final Service040109 service040109;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search040301 search,
                          Model model, Pageable pageable){
        log.info("판매현황 메인");
        log.info("menuId = {}", menuIdDto);
        log.info("search = {}", search);
        if(search.getSYmd1() == null || search.getSYmd2() == null){
            search.setYmd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "판매번호", "품목명", "규격", "수량", "단가",
                "공급가액", "부가세", "거래처", "프로젝트", "기타사항", "출고시참조"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        model.addAttribute("custGrCodes",service040101.findAllBySelect(1));
        model.addAttribute("projectCodes", service040109.findAllBySelect());

        Page<Dto040301> resultList = service040301.findAllByDsl(search, pageable);

        int price = 0, qty = 0, amt = 0, vat = 0;
        for(Dto040301 dto : resultList) {
            price += dto.getPrice();
            qty += dto.getQty();
            amt += dto.getAmt();
            vat += dto.getVat();
        }
        List<String> footer = GlobalMethod.makeFooter("", "", "", "", String.valueOf(qty), String.valueOf(price), String.valueOf(amt), String.valueOf(vat), "", "", "", "");
        model.addAttribute("footers", footer);

        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "business/analyze/040301";
    }

    @GetMapping("/excel")
    public void excel(@ModelAttribute("search") Search040301 search,
                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("판매현황 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "판매번호", "품목명", "규격", "수량", "단가",
                "공급가액", "부가세", "거래처", "프로젝트", "기타사항", "출고시참조"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "Tint",
                "Tint", "Tint", "Tint", "String", "String",
                "String", "String"
        );

        List<List<String>> excelData = service040301.findAllByExcel(search);
        String fileName = "판매현황(040301).xlsx";

        excelMaker.makeExcel("판매현황 (040301)", titles, excelData, excelType, fileName, response);
    }
}
