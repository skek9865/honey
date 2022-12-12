package project.honey.business.controller.basic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.honey.business.dto.basic.Tb401Dto;
import project.honey.business.form.basic.Tb401Form;
import project.honey.business.service.basic.Service040101;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.system.service.Service990301;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/040101")
public class Controller040101 {
    
    private final Service040101 service040101;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model, Pageable pageable){
        log.info("거래처그룹관리 메인");
        log.info("menuId = {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "거래처구분", "거래처구분코드", "거래처정렬순서",
                "거래처구분명"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb401Dto> resultList = service040101.findAll(pageable);
        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "business/040101";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("거래처그룹관리 input");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));
        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("classSeqCodes",service990301.findByFstId("08"));
        if(map.get("vseq").isEmpty()){
            model.addAttribute("dto",new Tb401Dto());
            return "business/040101_input";
        }
        model.addAttribute("dto", service040101.findById(Integer.parseInt(map.get("vseq"))));
        return "business/040101_input";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb401Form form, Model model, HttpServletRequest request){
        log.info("거래처그룹관리 insert");
        log.info("form = {}", form);
        if (service040101.insert(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb401Form form, Model model, HttpServletRequest request){
        log.info("거래처그룹관리 update");
        log.info("form = {}", form);
        if(service040101.update(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        log.info("거래처그룹관리 delete");
        if(service040101.delete(id)) model.addAttribute("msg", "정상적으로 삭제 되었습니다.");
        else model.addAttribute("msg", "문제가 발생하였습니다");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/excel")
    public void excel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("거래처그룹관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "거래처구분", "거래처구분코드", "거래처정렬순서",
                "거래처구분명"
        );
        List<String> excelType = GlobalMethod.makeExcelType("String", "String", "int", "String");

        List<List<String>> excelData = service040101.findAllByExcel();
        String fileName = "거래처그룹관리(040101).xlsx";

        excelMaker.makeExcel("거래처그룹관리 (040101)", titles, excelData, excelType, fileName, response);
    }
}
