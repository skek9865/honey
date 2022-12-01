package project.honey.personDepart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
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
import project.honey.personDepart.form.Tb202Form;
import project.honey.personDepart.dto.Tb202Dto;
import project.honey.personDepart.service.Service020102;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/020102")
public class Controller020102 {

    private final Service020102 service020102;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model, Pageable pageable){
        log.info("부서관리 메인");
        log.info("menuId = {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "부서코드", "부서명", "사용여부"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb202Dto> resultList = service020102.findAll(pageable);
        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "personDepart/020102";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("부서관리 input");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));
        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", new GlobalConst());
        if(map.get("vseq").isEmpty()){
            model.addAttribute("dto",new Tb202Dto());
            return "personDepart/020102_input";
        }
        model.addAttribute("dto", service020102.findById(Integer.parseInt(map.get("vseq"))));
        return "personDepart/020102_input";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb202Form form, Model model, HttpServletRequest request){
        log.info("부서관리 insert");
        log.info("form = {}", form);
        if (service020102.insert(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb202Form form, Model model, HttpServletRequest request){
        log.info("부서관리 update");
        log.info("form = {}", form);
        if(service020102.update(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        log.info("부서관리 delete");
        if(service020102.delete(id)) model.addAttribute("msg", "정상적으로 삭제 되었습니다.");
        else model.addAttribute("msg", "문제가 발생하였습니다");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/excel")
    public void excel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("부서관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "부서코드", "부서명", "사용여부"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String"
        );
        List<List<String>> excelData = service020102.findAllByExcel();
        String fileName = "부서관리(020102).xlsx";

        excelMaker.makeExcel("부서관리 (020102)", titles, excelData, excelType, fileName, response);
    }
}
