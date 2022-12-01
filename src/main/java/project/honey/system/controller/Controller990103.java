package project.honey.system.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.honey.comm.*;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.system.dto.Tb901Dto;
import project.honey.system.dto.Tb903Dto;
import project.honey.system.service.Service990101;
import project.honey.system.service.Service990103;
import project.honey.system.service.Service990201;
import project.honey.system.service.Service990301;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/990103")
public class Controller990103 {

    private final Service990101 service990101;
    private final Service990103 service990103;
    private final Service990201 service990201;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;
    private final CodeToName codeToName;

    // 사용자 저장
    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb903Dto dto, Model model, HttpServletRequest request) {
        log.info("user : " + dto);

        model.addAttribute("msg", service990103.insert(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model, Pageable pageable) {

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "사용자ID", "화면명", "접근여부", "목록사용여부",
                "건별조회사용여부", "저장사용여부","수정사용여부", "삭제사용여부"
        );

        // 임시
        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb903Dto> dtos = service990103.findAll(pageable);
        model.addAttribute("pageMaker", new PageMaker(pageable, dtos.getTotalElements()));
        model.addAttribute("dtos", dtos.getContent());

        return "system/990103";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb903Dto dto, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service990103.update(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{seq}")
    public String update(@PathVariable Integer seq, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service990103.delete(seq) != null ? "정상적으로 삭제 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }


    @GetMapping("/input")
    public String input(Model model, Integer seq){
        log.info("input");
        log.info("id = {}", seq);
        model.addAttribute("dto",
                seq != null
                        ? service990103.findById(seq)
                        : new Tb903Dto());

        model.addAttribute("users", service990101.userList());
        model.addAttribute("screens", service990201.findThdMenuAll());
        model.addAttribute("global", new GlobalConst());
        return "system/990103_input";
    }

    @GetMapping("/excel")
    public void excel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "사용자ID", "회원명", "접근여부", "목록사용여부",
                "건별조회사용여부", "저장사용여부","수정사용여부", "삭제사용여부"
        );

        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String", "String", "String",
                "String", "String"
        );

        List<List<String>> excelData = service990103.findAllByExcel();
        String fileName = "시스템사용이력현황(990103)";
        excelMaker.makeExcel("시스템사용이력현황 (990103)", titles, excelData, excelType, fileName, response);
    }

}
