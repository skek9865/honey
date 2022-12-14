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
import project.honey.personDepart.dto.Tb204Dto;
import project.honey.personDepart.form.Tb204Form;
import project.honey.personDepart.service.Service020202;
import project.honey.system.service.Service990101;
import project.honey.system.service.Service990301;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/020202")
public class Controller020202 {

    private final Service020202 service020202;
    private final Service990101 service990101;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;
    private final HttpSession session;

    @GetMapping("")
    public String findAll(@RequestParam Map<String, String> map, @ModelAttribute("menuId") MenuIdDto menuIdDto,
                          Model model, Pageable pageable){
        log.info("회사파일관리 메인");
        log.info("menuId = {}", menuIdDto);
        log.info("sOutFNm = {}, sPart = {}", map.get("sOutFNm"), map.get("sPart"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "구분", "파일", "비고"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        model.addAttribute("sOutFNm", map.get("sOutFNm"));
        model.addAttribute("sPart", map.get("sPart"));

        model.addAttribute("partCodes",service990301.findByFstId("25"));

        Page<Tb204Dto> resultList = service020202.findAllByDsl(map.get("sOutFNm"), map.get("sPart"), pageable);
        model.addAttribute("dtos",resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "personDepart/020202";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("회사파일관리 input");
        log.info("fstId = {}", map.get("fstId"));
        log.info("scdId = {}", map.get("scdId"));
        log.info("thdId = {}", map.get("thdId"));

//        String user = session.getAttribute("user").toString();
//        Tb901Dto entity901 = service990101.findById(user);
//        String empNo = entity901.getEmpNo();

        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("partCodes",service990301.findByFstId("25"));
        if(map.get("vseq").isEmpty()){
            model.addAttribute("dto",new Tb204Dto(27));
            return "personDepart/020202_input";
        }
        model.addAttribute("dto", service020202.findById(Integer.parseInt(map.get("vseq"))));
        return "personDepart/020202_input";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb204Form form, Model model, HttpServletRequest request) throws IOException {
        log.info("회사파일관리 insert");
        log.info("form = {}", form);
        if (service020202.insert(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb204Form form, Model model, HttpServletRequest request){
        log.info("회사파일관리 update");
        log.info("form = {}", form);
        if(service020202.update(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        log.info("회사파일관리 delete");
        if(service020202.delete(id)) model.addAttribute("msg", "정상적으로 삭제 되었습니다.");
        else model.addAttribute("msg", "문제가 발생하였습니다");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/excel")
    public void excel(@RequestParam Map<String, String> map, HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("회사파일관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "구분", "파일", "비고"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String"
        );
        String fileName = "회사파일관리(020202).xlsx";
        List<List<String>> excelData = service020202.findAllByExcel(map.get("sOutFNm"), map.get("sPart"));

        excelMaker.makeExcel("회사파일관리 (020202)", titles, excelData, excelType, fileName, response);
    }
}
