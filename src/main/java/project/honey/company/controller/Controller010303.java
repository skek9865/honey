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
import project.honey.company.dto.Tb107Dto;
import project.honey.company.dto.Tb108Dto;
import project.honey.company.form.Tb107Form;
import project.honey.company.form.Tb108Form;
import project.honey.company.service.Service010303;

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
@RequestMapping("/010303")
public class Controller010303 {

    private final Service010303 service010303;
    private final ExcelMaker excelMaker;
    private final MenuMaker menuMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model, Pageable pageable){
        log.info("특허관리 메인");
        log.info("menuId = {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "구분", "특허번호/등록번호", "출원번호", "출원일",
                "등록일", "명칭/물품류", "특허권자", "발명자/창작자", "발급처", "비고"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb108Dto> resultList = service010303.findAll(pageable);
        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "company/010303";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("특허관리 input");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));
        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", new GlobalConst());
        if(map.get("vseq").isEmpty()){
            model.addAttribute("dto",new Tb108Dto(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            return "company/010303_input";
        }
        model.addAttribute("dto", service010303.findById(Integer.parseInt(map.get("vseq"))));
        return "company/010303_input";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb108Form from, Model model, HttpServletRequest request) throws IOException {
        log.info("특허관리 insert");
        log.info("from = {}", from);
        if (service010303.insert(from)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb108Form form, Model model, HttpServletRequest request) throws IOException {
        log.info("특허관리 update");
        log.info("dto = {}", form);
        if(service010303.update(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        log.info("특허관리 delete");
        if(service010303.delete(id)) model.addAttribute("msg", "정상적으로 삭제 되었습니다.");
        else model.addAttribute("msg", "문제가 발생하였습니다");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/excel")
    public void excel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("특허관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "구분", "특허번호/등록번호", "출원번호", "출원일",
                "등록일", "명칭/물품류", "특허권자", "발명자/창작자", "발급처", "비고"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String",
                "String", "String", "String", "String", "String", "String"
        );

        List<List<String>> excelData = service010303.findAllByExcel();
        String fileName = "특허관리(010303).xlsx";

        excelMaker.makeExcel("특허관리 (010303)", titles, excelData, excelType, fileName, response);
    }
}
