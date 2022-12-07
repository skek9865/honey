package project.honey.system.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.honey.comm.*;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.system.dto.Tb901Dto;
import project.honey.system.dto.Tb902Dto;
import project.honey.system.dto.Tb903Dto;
import project.honey.system.service.Service990101;
import project.honey.system.service.Service990102;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/990102")
public class Controller990102 {

    private final Service990101 service990101;
    private final Service990102 service990102;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;
    private final CodeToName codeToName;

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb902Dto dto, Model model, HttpServletRequest request) {
        log.info("dto : " + dto);

        model.addAttribute("msg", service990102.insert(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model, Pageable pageable) {

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "아이디", "접속시간", "접속IP", "접속port",
                "섹션", "사용자사양"
        );

        // 임시
        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb902Dto> dtos = service990102.findAll(pageable);
        model.addAttribute("pageMaker", new PageMaker(pageable, dtos.getTotalElements()));
        model.addAttribute("dtos", dtos.getContent());

        return "system/990102";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb902Dto dto, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service990102.update(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{seq}")
    public String update(@PathVariable Integer seq, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service990102.delete(seq) != null ? "정상적으로 삭제 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/input")
    public String input(Model model, Integer id){
        log.info("input");
        log.info("id = {}", id);
        model.addAttribute("dto",
                id != null
                        ? service990102.findById(id)
                        : new Tb902Dto());
        model.addAttribute("users", service990101.userList());
        model.addAttribute("global", new GlobalConst());
        return "system/990102_input";
    }

    @GetMapping("/excel")
    public void excel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "아이디", "접속시간", "접속IP", "접속port",
                "섹션", "사용자사양"
        );

        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "int", "String", "String"
        );

        List<List<String>> excelData = service990102.findAllByExcel();
        String fileName = "시스템사용이력현황(990102)";
        excelMaker.makeExcel("시스템사용이력현황 (990102)", titles, excelData, excelType,fileName, response);
    }
}
