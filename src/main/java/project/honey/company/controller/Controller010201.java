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
import project.honey.company.dto.Tb102Dto;
import project.honey.company.service.Service010201;
import project.honey.system.service.Service990301;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/010201")
public class Controller010201 {

    private final MenuMaker menuMaker;
    private final Service010201 service010201;
    private final Service990301 service990301;
    private final ExcelMaker excelMaker;

    @GetMapping()
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model, Pageable pageable){

        Page<Tb102Dto> resultList = service010201.findAll(pageable);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "계좌번호", "은행명", "예금주", "용도",
                "이용자ID", "개설일", "적요", "참조", "사용여부"
        );

        model.addAttribute("global", new GlobalConst());
        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));


        model.addAttribute("titles",titles);
        model.addAttribute("dtos",resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "company/010201";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("fstId = {}", map.get("fstId"));
        log.info("scdId = {}", map.get("scdId"));
        log.info("thdId = {}", map.get("thdId"));
        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("bankNmCodes",service990301.findByFstId("04"));
        if(map.get("vseq").isEmpty()){
            model.addAttribute("dto",new Tb102Dto());
            return "company/010201_input";
        }
        model.addAttribute("dto", service010201.findById(Integer.parseInt(map.get("vseq"))));
        return "company/010201_input";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb102Dto dto, Model model, HttpServletRequest request){
        log.info("dto : {}", dto);

        model.addAttribute("msg", service010201.insert(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));

        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb102Dto dto, Model model, HttpServletRequest request){
        log.info("dto : {}", dto);

        model.addAttribute("msg", service010201.update(dto) != null ? "정상적으로 수정 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));

        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model ,HttpServletRequest request) {

        log.info("id : " + id);

        model.addAttribute("msg", service010201.delete(id) != null ? "정상적으로 삭제 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/excel")
    public void excel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "계좌번호", "은행명", "예금주", "용도",
                "이용자ID", "개설일","적요","참조","사용여부"
        );

        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String", "String",
                "String", "String", "String", "String"
        );

        List<List<String>> excelData = service010201.findAllByExcel();
        String fileName = "통장관리(010201)";
        excelMaker.makeExcel("통장관리 (010201)", titles, excelData, excelType,fileName, response);
    }
}
