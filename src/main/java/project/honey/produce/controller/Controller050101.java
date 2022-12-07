package project.honey.produce.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.produce.dto.Tb501Dto;
import project.honey.produce.service.Service050101;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/050101")
public class Controller050101 {

    private final Service050101 service050101;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model, Pageable pageable) {

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "생산공정코드", "생산공정정렬순서", "생산공정명"
        );
        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb501Dto> result = service050101.findAll(pageable);

        model.addAttribute("pageMaker", new PageMaker(pageable, result.getTotalElements()));
        model.addAttribute("dtos", result.getContent());

        return "produce/050101";
    }

    @GetMapping("/{seq}")
    @ResponseBody
    public ResponseEntity<Tb501Dto> findAll(@PathVariable Integer seq) {
        log.info("seq : " + seq);
        return new ResponseEntity<>(service050101.findById(seq), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb501Dto dto, Model model, HttpServletRequest request) {
        log.info("dto : {}", dto);


        model.addAttribute("msg", service050101.insert(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb501Dto dto, Model model, HttpServletRequest request) {



        model.addAttribute("msg", service050101.update(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Integer id) {

        log.info("id : " + id);
        service050101.delete(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @GetMapping("/input")
    public String input(Model model,String action, Integer seq){
        log.info("input");
        log.info("seq = {}", seq);
        model.addAttribute("action", action);
        model.addAttribute("dto",
                seq != null
                        ? service050101.findById(seq)
                        : new Tb501Dto());
        model.addAttribute("global", new GlobalConst());
        return "produce/050101_input";
    }

    @GetMapping("/excel")
    public void excel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번","생산공정코드", "생산공정정렬순서", "생산공정명"
        );

        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "int", "String"
        );

        List<List<String>> excelData = service050101.findAllByExcel();
        String fileName = "생산공정관리(050101)";
        excelMaker.makeExcel("생산공정관리 (050101)", titles, excelData, excelType, fileName, response);
    }
}
