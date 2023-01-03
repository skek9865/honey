package project.honey.company.controller;

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
import project.honey.company.dto.Tb106Dto;
import project.honey.company.dto.Tb106_1Dto;
import project.honey.company.form.Tb106Form;
import project.honey.company.form.Tb106_1Form;
import project.honey.company.service.Service010301;
import project.honey.personDepart.service.Service020101;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/010301")
public class Controller010301 {

    private final Service010301 service010301;
    private final Service020101 service020101;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model, Pageable pageable){
        log.info("법인자동차관리 메인");
        log.info("menuId = {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "자동차명", "연식", "차량번호", "관리자",
                "보험회사", "구입일", "구입금액", "비고"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb106Dto> resultList = service010301.findAll(pageable);
        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        int buyAmt = 0;
        for(Tb106Dto dto : resultList) buyAmt += dto.getBuyAmt();
        List<String> footer = GlobalMethod.makeFooter("", "", "", "", "", "", "", "", String.valueOf(buyAmt), "");
        model.addAttribute("footers", footer);

        return "company/010301";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("법인자동차관리 input");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));
        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("empCodes",service020101.findAllBySelect());
        if(map.get("vseq").isEmpty()){
            model.addAttribute("dto",new Tb106Dto(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString(), 0, 27));
            return "company/010301_input";
        }
        model.addAttribute("dto", service010301.findById(Integer.parseInt(map.get("vseq"))));
        return "company/010301_input";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb106Form form, Model model, HttpServletRequest request){
        log.info("법인자동차관리 insert");
        log.info("form = {}", form);
        if (service010301.insert(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb106Form form, Model model, HttpServletRequest request){
        log.info("법인자동차관리 update");
        log.info("form = {}", form);
        if(service010301.update(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        log.info("법인자동차관리 delete");
        if(service010301.delete(id)) model.addAttribute("msg", "정상적으로 삭제 되었습니다.");
        else model.addAttribute("msg", "문제가 발생하였습니다");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/excel")
    public void excel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("법인자동차관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "자동차명", "연식", "차량번호", "관리자",
                "보험회사", "구입일", "구입금액", "비고"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String",
                "String", "String", "Tint", "String"
        );

        List<List<String>> excelData = service010301.findAllByExcel();
        String fileName = "법인자동차관리(010301).xlsx";

        excelMaker.makeExcel("법인자동차관리 (010301)", titles, excelData, excelType, fileName, response);
    }

    @GetMapping("/fk/{id}")
    public String findAllFk(@PathVariable("id") Integer id, @ModelAttribute("menuId") MenuIdDto menuIdDto, Model model){
        log.info("운행관리 메인");
        log.info("menuId = {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "운행시작일자", "운행종료일자", "운행자",
                "금액", "과태료", "출장지", "출장목적", "비고"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("fk", String.valueOf(id));

        List<Tb106_1Dto> resultList = service010301.findAllFK(id);
        model.addAttribute("dtos", resultList);

        return "company/010301_1";
    }

    @GetMapping("/fk/input")
    public String findByIdFk(@RequestParam Map<String, String> map, Model model){
        log.info("운행관리 input");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));
        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("empCodes",service020101.findAllBySelect());
        if(map.get("vseq").isEmpty()){
            Integer fk = Integer.parseInt(map.get("vseq1"));
            model.addAttribute("dto",new Tb106_1Dto(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString(), 0, 27, fk));
            return "company/010301_1_input";
        }
        model.addAttribute("dto", service010301.findByIdFk(Integer.parseInt(map.get("vseq"))));
        return "company/010301_1_input";
    }

    @PostMapping("/fk/insert")
    public String insertFk(@ModelAttribute Tb106_1Form form, Model model, HttpServletRequest request){
        log.info("운행관리 insert");
        log.info("form = {}", form);
        if (service010301.insertFk(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/fk/update")
    public String updateFk(@ModelAttribute Tb106_1Form form, Model model, HttpServletRequest request){
        log.info("운행관리 update");
        log.info("form = {}", form);
        if(service010301.updateFk(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/fk/delete/{id}")
    public String deleteFk(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        log.info("운행관리 delete");
        if(service010301.deleteFk(id)) model.addAttribute("msg", "정상적으로 삭제 되었습니다.");
        else model.addAttribute("msg", "문제가 발생하였습니다");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/fk/excel/{id}")
    public void excelFk(@PathVariable("id") Integer id,HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("운행관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "운행시작일자", "운행종료일자", "운행자",
                "금액", "과태료", "출장지", "출장목적", "비고"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "int",
                "int", "String", "String", "String"
        );

        List<List<String>> excelData = service010301.findAllByExcelFk(id);
        String fileName = "운행관리.xlsx";

        excelMaker.makeExcel("운행관리", titles, excelData, excelType, fileName, response);
    }
}
