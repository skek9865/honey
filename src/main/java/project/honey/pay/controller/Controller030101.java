package project.honey.pay.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
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
import project.honey.pay.dto.Tb301Dto;
import project.honey.pay.entity.Tb301;
import project.honey.pay.service.Service030101;
import project.honey.personDepart.dto.Tb201Dto;
import project.honey.system.dto.Tb906Dto;
import project.honey.system.entity.Tb903;
import project.honey.system.service.Service990301;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/030101")
public class Controller030101 {

    private final Service030101 service030101;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model,
                          Pageable pageable) {

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "공제/지급구분", "과세여부", "항목코드", "항목명", "세제율", "사용여부"
        );
        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb301Dto> list = service030101.findAll(pageable);

        model.addAttribute("pageMaker", new PageMaker(pageable, list.getTotalElements()));
        model.addAttribute("list", list.getContent());

        return "pay/030101";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb301Dto dto, Model model,HttpServletRequest request) {
        log.info("dto : {}", dto);

        model.addAttribute("msg", service030101.insert(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));

        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb301Dto dto, Model model, HttpServletRequest request) {

        model.addAttribute("msg", service030101.update(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Integer id) {

        log.info("id : " + id);
        service030101.delete(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @GetMapping("/input")
    public String input(Model model, Integer id){
        log.info("input");
        log.info("id = {}", id);
        model.addAttribute("dto",
                id != null
                        ? service030101.findById(id)
                        : new Tb301Dto());
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("codes", service990301.findByFstId("05"));
        return "pay/030101_input";
    }

    @GetMapping("/excel")
    public void excel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "공제/지급구분", "과세여부", "항목코드", "항목명", "세제율", "사용여부"
        );

        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String", "String", "String"
        );

        List<List<String>> excelData = service030101.findAllByExcel();
        String fileName = "급여항목관리(030101).xls";
        excelMaker.makeExcel("급여항목관리 (030101)", titles, excelData, excelType,fileName, response);
    }
}
