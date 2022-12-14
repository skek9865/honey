package project.honey.personDepart.controller;

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
import project.honey.personDepart.form.Tb201Form;
import project.honey.personDepart.dto.Tb201Dto;
import project.honey.personDepart.service.Service020101;
import project.honey.personDepart.service.Service020102;
import project.honey.system.service.Service990301;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/020101")
public class Controller020101 {

    private final Service020101 service020101;
    private final Service020102 service020102;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@RequestParam Map<String, String> map,
                          @ModelAttribute("menuId")MenuIdDto menuIdDto, Model model, Pageable pageable){
        log.info("사원관리 메인");
        log.info("empNm = {}, postCd = {}, deptCd = {}" ,map.get("sEmpNm"), map.get("sPost"), map.get("sDeptCd"));
        log.info("menuId = {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "사원번호", "사원명", "입사일자",
                "직위/직급", "전화번호", "모바일", "Email", "부서명", "업무코드"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        model.addAttribute("postCodes",service990301.findByFstId("01"));
        model.addAttribute("deptCdCodes",service020102.findAllDept());

        model.addAttribute("sEmpNm",  map.get("sEmpNm"));
        model.addAttribute("sPost",   map.get("sPost"));
        model.addAttribute("sDeptCd", map.get("sDeptCd"));

        Page<Tb201Dto> resultList = service020101.findAllByDsl(map.get("sEmpNm"), map.get("sPost"), map.get("sDeptCd"), pageable);
        model.addAttribute("dtos",resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "personDepart/020101";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("사원관리 input");
        log.info("fstId = {}", map.get("fstId"));
        log.info("scdId = {}", map.get("scdId"));
        log.info("thdId = {}", map.get("thdId"));
        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("postCodes",service990301.findByFstId("01"));
        model.addAttribute("empClassCodes",service990301.findByFstId("02"));
        model.addAttribute("post1Codes",service990301.findByFstId("03"));
        model.addAttribute("bankNmCodes",service990301.findByFstId("04"));
        model.addAttribute("deptCdCodes",service020102.findAllDept());
        if(map.get("vseq").isEmpty()){
            model.addAttribute("dto",new Tb201Dto());
            return "personDepart/020101_input";
        }

        Tb201Dto dto = service020101.findById(Integer.parseInt(map.get("vseq")));
        String fileName = "/files/emp/" + makeFileName(dto.getFileNm(), dto.getSeq());
        String imgName = "/images/emp/" + makeFileName(dto.getPicNm(), dto.getSeq());

        model.addAttribute("dto", dto);
        model.addAttribute("fileName", fileName);
        model.addAttribute("imgName", imgName);
        return "personDepart/020101_input";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb201Form form, Model model, HttpServletRequest request) throws IOException {
        log.info("사원관리 insert");
        log.info("form = {}", form);
        if (service020101.insert(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb201Form form, Model model, HttpServletRequest request){
        log.info("사원관리 update");
        log.info("form = {}", form);
        if(service020101.update(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        log.info("사원관리 delete");
        if(service020101.delete(id)) model.addAttribute("msg", "정상적으로 삭제 되었습니다.");
        else model.addAttribute("msg", "문제가 발생하였습니다");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/excel")
    public void excel(@RequestParam Map<String, String> map, HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("사원관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "사원번호", "사원명", "입사일자",
                "직위/직급", "전화번호", "모바일", "Email", "부서명", "업무코드"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String", "String",
                "String", "String", "String", "String"
        );

        List<List<String>> excelData = service020101.findAllByExcel(map.get("sEmpNm"), map.get("sPost"), map.get("sDeptCd"));
        String fileName = "사원관리(020101).xls";

        excelMaker.makeExcel("사원관리 (020101)", titles, excelData, excelType, fileName, response);
    }

    private String makeFileName(String originalFilename, Integer id){
        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1);
        return String.valueOf(id) + "." + ext;
    }
}