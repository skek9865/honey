package project.honey.produce.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.honey.business.service.basic.Service040103;
import project.honey.business.service.basic.Service040109;
import project.honey.comm.*;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.produce.dto.Tb505Dto;
import project.honey.produce.dto.Tb505_1Dto;
import project.honey.produce.dto.Tb506Dto;
import project.honey.produce.dto.Tb506_1Dto;
import project.honey.produce.dto.form.Tb505Form;
import project.honey.produce.dto.form.Tb506Form;
import project.honey.produce.dto.search.Search050301;
import project.honey.produce.dto.search.Search050302;
import project.honey.produce.service.Service050301;
import project.honey.produce.service.Service050302;
import project.honey.system.service.Service990301;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/050302")
public class Controller050302 {


    private final Service040103 service040103;
    private final Service040109 service040109;
    private final Service050302 service050302;
    private final Service990301 service990301;
    private final CodeToName codeToName;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search050302 search, Model model, Pageable pageable) {

        // 날짜 기본값 세팅
        if(search.getYmd1() == null){
            search.setYmd1(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            search.setYmd2(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "입고순번", "입고생산공장", "받는창고","품목", "수량", "상태"
        );
        model.addAttribute("menus", menuMaker.getMenuId(1, "", "", ""));
        model.addAttribute("menuNm", menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles", titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb506Dto> result = service050302.findAll(search, pageable);
        List<Tb506Dto> content = result.getContent();
        // 푸터
        int qty = 0;
        for (Tb506Dto dto : content) {
            qty += dto.getQty() != null ? dto.getQty() : 0;
        }
        List<String> footers = GlobalMethod.makeFooter(
                "", "", "", "", "","", String.valueOf(qty), "");

        model.addAttribute("footers", footers);
        model.addAttribute("statuses", service990301.findByFstId("12"));
        model.addAttribute("pageMaker", new PageMaker(pageable, result.getTotalElements()));
        model.addAttribute("dtos", content);

        return "produce/050302";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb506Form dto, Model model, HttpServletRequest request) {
        log.info("dto : {}", dto);
        model.addAttribute("msg", service050302.insert(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb506Form dto, Model model, HttpServletRequest request) {
        log.info("dto : {}", dto);
        model.addAttribute("msg", service050302.update(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{seq}")
    public String delete(@PathVariable Integer seq, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service050302.delete(seq) != null ? "정상적으로 삭제 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }
    @GetMapping("/input")
    public String input(Model model,String action, Integer seq){

        log.info("input");
        log.info("seq = {}", seq);

        List<String> titles = GlobalMethod.makeTitle(
                "삭제","코드", "품목","규격","수량", "적요"
        );

        model.addAttribute("statuses", service990301.findByFstId("12"));
        model.addAttribute("projects", service040109.findAllBySelect());
        model.addAttribute("wHouses", service040103.findAllBySelect());

        // 퇴사한 인원들 안나와서 임시로 codeToName 사용용
//       model.addAttribute("emps", service020101.findAllBySelect());
        model.addAttribute("emps", codeToName.emp());

        model.addAttribute("action", action);
        model.addAttribute("titles", titles);
        model.addAttribute("global", new GlobalConst());

        Tb506Form form = service050302.findById(seq);
        List<Tb506_1Dto> dtoList = form.getTb506_1Dtos();
        int qty = 0;
        for (Tb506_1Dto dto : dtoList) {
            qty += dto != null ? dto.getQty() : 0;
        }
        model.addAttribute("dto", form);
        model.addAttribute("qty_tot", qty);

        return "produce/050302_input";
    }

    @GetMapping("/excel")
    public void excel(Search050302 search, HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("작업지시서관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "입고순번", "입고생산공장","받는창고", "품목", "수량", "상태"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String", "int", "String"
        );

        List<List<String>> excelData = service050302.findAllByExcel(search);
        String fileName = "생산입고(050302).xls";

        excelMaker.makeExcel("생산입고 (050302)", titles, excelData, excelType, fileName, response);
    }
}
