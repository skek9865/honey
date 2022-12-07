package project.honey.produce.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import project.honey.business.dto.search.Search405;
import project.honey.business.dto.search.SearchPopUp405;
import project.honey.business.service.Service040105;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.produce.dto.Tb502Dto;
import project.honey.produce.dto.Tb503Dto;
import project.honey.produce.dto.input.Tb503Input;
import project.honey.produce.service.Service050103;
import project.honey.system.service.Service990301;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/050103")
public class Controller050103 {

    private final Service040105 service040105;
    private final Service050103 service050103;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search405 search, Model model, Pageable pageable) {

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "코드", "품목명", "품목구분", "생산공정", "원자재수량", "BOM 관리"
        );
        model.addAttribute("menus", menuMaker.getMenuId(1, "", "", ""));
        model.addAttribute("menuNm", menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles", titles);
        model.addAttribute("global", new GlobalConst());

        // 셀렉트박스 데이터
        model.addAttribute("classSeqCodes", service990301.findByFstId("07"));

        Page<Tb503Dto> result = service050103.findAll(search, pageable);
        List<Tb503Dto> content = result.getContent();
        int sum = content.stream().mapToInt(Tb503Dto::getQty).sum();

        // 푸터
        List<String> footers = GlobalMethod.makeFooter("", "", "", "", "", String.valueOf(sum), "");

        model.addAttribute("footers", footers);
        model.addAttribute("pageMaker", new PageMaker(pageable, result.getTotalElements()));
        model.addAttribute("dtos", content);

        return "produce/050103";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb503Input dto, Model model, HttpServletRequest request) {
        log.info("dto : {}", dto);
        model.addAttribute("msg", service050103.insert(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb503Input dto, Model model, HttpServletRequest request) {
        log.info("dto : {}", dto);
        model.addAttribute("msg", service050103.update(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{seq}")
    public String delete(@PathVariable Integer seq, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service050103.delete(seq) != null ? "정상적으로 삭제 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/popup")
    public String popup(@ModelAttribute("search") SearchPopUp405 search, Model model) {
        model.addAttribute("global", new GlobalConst());

        List<String> titles = GlobalMethod.makeTitle(
                "코드", "품목명", "규격", "생산공정", "재고", "선택"
        );

        if(!search.isProduct()) titles.remove("생산공정");
        if(!search.isStock()) titles.remove("재고");

        model.addAttribute("titles", titles);

        if (StringUtils.hasText(search.getGoodsNm())) {
            model.addAttribute("dtos", service040105.findAllByPopUp(search));
        } else {
            model.addAttribute("dtos", null);
        }
        return "produce/050103_1";
    }

    @GetMapping("/input")
    public String input(Model model,String action,String goodsCd, Integer seq){
        log.info("input");
        log.info("seq = {}", seq);

        List<String> titles = GlobalMethod.makeTitle(
                "삭제","코드", "품목명", "규격","단위","수량", "생산공정", "위치",
                "적요"
        );

        model.addAttribute("action", action);
        model.addAttribute("titles", titles);
        model.addAttribute("dto", service050103.findById(seq, goodsCd));
        model.addAttribute("global", new GlobalConst());
        return "produce/050103_input";
    }

    @GetMapping("/excel")
    public void excel(Search405 search, HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("BOM(소요량)조회 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "코드", "품목명", "품목구분", "생산공정", "원자재수량"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String", "int"
        );

        List<List<String>> excelData = service050103.findAllByExcel(search);
        String fileName = "BOM(소요량)조회(050103).xls";

        excelMaker.makeExcel("BOM(소요량)조회 (050103)", titles, excelData, excelType, fileName, response);
    }
}
