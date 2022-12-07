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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.InputMenuIdDto;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.system.dto.Tb901Dto;
import project.honey.system.dto.Tb906Dto;
import project.honey.system.service.Service990101;
import project.honey.system.service.Service990301;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/990301")
public class Controller990301 {

    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model,
                          String sfstid, Pageable pageable) {

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "대그룹ID", "중그룹ID", "정렬순번", "코드명"
        );
        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb906Dto> codes = service990301.findAll(pageable, sfstid);

        // 검색 데이터 유지
        model.addAttribute("sfstid", sfstid);

        // 대그룹
        model.addAttribute("codes", service990301.findFstIdAll());

        model.addAttribute("pageMaker", new PageMaker(pageable, codes.getTotalElements()));
        model.addAttribute("list", codes.getContent());

        return "system/990301";
    }

    @GetMapping("/{seq}")
    @ResponseBody
    public ResponseEntity<Tb906Dto> findAll(@PathVariable Integer seq) {
        log.info("seq : " + seq);
        return new ResponseEntity<>(service990301.findById(seq), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb906Dto dto, Model model, HttpServletRequest request) {
        log.info("dto : {}", dto);


        model.addAttribute("msg", service990301.insert(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb906Dto dto, Model model, HttpServletRequest request) {



        model.addAttribute("msg", service990301.update(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Integer id) {

        log.info("id : " + id);
        service990301.delete(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @GetMapping("/input")
    public String input(Model model, Integer id){
        log.info("input");
        log.info("id = {}", id);
        model.addAttribute("dto",
                id != null
                        ? service990301.findById(id)
                        : new Tb906Dto());
        model.addAttribute("global", new GlobalConst());
        return "system/990301_input";
    }

    @GetMapping("/excel")
    public void excel(String sfstid, HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "대그룹ID", "중그룹ID", "정렬순번", "코드명"
        );

        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String"
        );

        List<List<String>> excelData = service990301.findAllByExcel(sfstid);
        String fileName = "공통코드관리(990301)";
        excelMaker.makeExcel("공통코드관리 (990301)", titles, excelData, excelType, fileName, response);
    }
}
