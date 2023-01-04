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
import project.honey.company.dto.Tb107Dto;
import project.honey.company.form.Tb106Form;
import project.honey.company.form.Tb107Form;
import project.honey.company.service.Service010302;
import project.honey.personDepart.service.Service020101;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/010302")
public class Controller010302 {

    private final Service010302 service010302;
    private final Service020101 service020101;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model, Pageable pageable){
        log.info("보험관리 메인");
        log.info("menuId = {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "자동차명", "연식", "차량번호", "관리자",
                "보험회사", "보험시작일", "보험종료일", "연령한정", "보험료", "비고"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb107Dto> resultList = service010302.findAll(pageable);
        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        int instAmt = 0;
        for(Tb107Dto dto : resultList) instAmt += dto.getInstAmt();
        List<String> footer = GlobalMethod.makeFooter("", "", "", "", "", "", "", "", "", "", String.valueOf(instAmt), "");
        model.addAttribute("footers", footer);

        return "company/010302";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("보험관리 input");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));
        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("empCodes",service020101.findAllBySelect());
        if(map.get("vseq").isEmpty()){
            model.addAttribute("dto",new Tb107Dto(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            return "company/010302_input";
        }
        model.addAttribute("dto", service010302.findById(Integer.parseInt(map.get("vseq"))));
        return "company/010302_input";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb107Form from, Model model, HttpServletRequest request){
        log.info("보험관리 insert");
        log.info("dto = {}", from);
        if (service010302.insert(from)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb107Form form, Model model, HttpServletRequest request){
        log.info("보험관리 update");
        log.info("dto = {}", form);
        if(service010302.update(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        log.info("보험관리 delete");
        if(service010302.delete(id)) model.addAttribute("msg", "정상적으로 삭제 되었습니다.");
        else model.addAttribute("msg", "문제가 발생하였습니다");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/excel")
    public void excel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("보험관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "자동차명", "연식", "차량번호", "관리자",
                "보험회사", "보험시작일", "보험종료일", "연령한정", "보험료", "비고"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String",
                "String", "String", "String", "String", "Tint", "String"
        );

        List<List<String>> excelData = service010302.findAllByExcel();
        String fileName = "보험관리(010302).xlsx";

        excelMaker.makeExcel("보험관리 (010302)", titles, excelData, excelType, fileName, response);
    }
}
