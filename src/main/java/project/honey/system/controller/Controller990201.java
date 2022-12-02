package project.honey.system.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.system.dto.Tb903Dto;
import project.honey.system.dto.Tb904Dto;
import project.honey.system.service.Service990201;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/990201")
public class Controller990201 {

    private final Service990201 service990201;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb904Dto dto, Model model, HttpServletRequest request) {
        log.info("user : " + dto);

        model.addAttribute("msg", service990201.insert(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model, Pageable pageable) {

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "대그룹ID", "중그룹ID", "소그룹ID", "정렬순번",
                "메뉴명", "메뉴url","사용여부"
        );

        // 임시
        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb904Dto> dtos = service990201.findAll(pageable);
        model.addAttribute("pageMaker", new PageMaker(pageable, dtos.getTotalElements()));
        model.addAttribute("dtos", dtos.getContent());

        return "system/990201";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb904Dto dto, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service990201.update(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{seq}")
    public String update(@PathVariable Integer seq, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service990201.delete(seq) != null ? "정상적으로 삭제 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/input")
    public String input(Model model, Integer seq){
        log.info("input");
        log.info("id = {}", seq);
        model.addAttribute("dto",
                seq != null
                        ? service990201.findById(seq)
                        : new Tb904Dto());
        model.addAttribute("global", new GlobalConst());
        return "system/990201_input";
    }

    @GetMapping("/excel")
    public void excel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "대그룹ID", "중그룹ID", "소그룹ID", "정렬순번",
                "메뉴명", "메뉴url","사용여부"
        );

        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "int", "String", "String",
                "String"
        );

        List<List<String>> excelData = service990201.findAllByExcel();
        String fileName = "메뉴관리(990201)";
        excelMaker.makeExcel("메뉴관리 (990201)", titles, excelData, excelType, fileName, response);
    }
}
