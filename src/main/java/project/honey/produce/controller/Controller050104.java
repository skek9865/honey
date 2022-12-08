package project.honey.produce.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import project.honey.business.dto.search.Search405;
import project.honey.business.service.Service040105;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.produce.dto.Dto050104;
import project.honey.produce.dto.Tb503Dto;
import project.honey.produce.service.Service050103;
import project.honey.produce.service.Service050104;
import project.honey.system.service.Service990301;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/050104")
public class Controller050104 {

    private final Service040105 service040105;
    private final Service050104 service050104;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search405 search, Model model, Pageable pageable) {

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "BOM품목구분", "BOM품목코드", "BOM품목","BOM규격명", "BOM수량",
                "품목구분", "품목코드", "품목", "규격명", "수량"
        );
        model.addAttribute("menus", menuMaker.getMenuId(1, "", "", ""));
        model.addAttribute("menuNm", menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles", titles);
        model.addAttribute("global", new GlobalConst());

        Page<Dto050104> result = service050104.findAll(search.getGoodsCd(), pageable);
        List<Dto050104> content = result.getContent();
        int qty = 0;
        int fQty = 0;
        for (Dto050104 dto : content) {
            qty += dto.getQty() != null ? dto.getQty() : 0;
            fQty += dto.getFQty() != null ? dto.getFQty() : 0;
        }
        // 푸터
        List<String> footers = GlobalMethod.makeFooter(
                "", "", "", "", "", String.valueOf(qty),
                "", "","","",String.valueOf(fQty));

        model.addAttribute("footers", footers);
        model.addAttribute("pageMaker", new PageMaker(pageable, result.getTotalElements()));
        model.addAttribute("dtos", content);

        return "produce/050104";
    }

    @GetMapping("/excel")
    public void excel(Search405 search, HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("BOM(소요량)조회 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "BOM품목구분", "BOM품목코드", "BOM품목","BOM규격명", "BOM수량",
                "품목구분", "품목코드", "품목", "규격명", "수량"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String", "int",
                "String", "String", "String", "String", "int"
        );

        List<List<String>> excelData = service050104.findAllByExcel(search.getGoodsCd());
        String fileName = "BOM(소요량)현황(050104).xls";

        excelMaker.makeExcel("BOM(소요량)현황 (050104)", titles, excelData, excelType, fileName, response);
    }
}
