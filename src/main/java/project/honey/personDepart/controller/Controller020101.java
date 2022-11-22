package project.honey.personDepart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.personDepart.dto.Form020101;
import project.honey.personDepart.dto.Tb201Dto;
import project.honey.personDepart.service.Service020101;
import project.honey.system.service.Service990301;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/020101")
public class Controller020101 {

    private final Service020101 service020101;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;

    @GetMapping("")
    public String findAll(@RequestParam Map<String, String> map,
                          @ModelAttribute("menuId")MenuIdDto menuIdDto, Model model, Pageable pageable){
        log.info("empNm = {}, postCd = {}, deptCd = {}" ,map.get("sEmpNm"), map.get("sPost"), map.get("sDeptCd"));
        log.info("menuId = {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "인증서명","만료일","사용용도", "보관형태"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        model.addAttribute("codes",service990301.findByFstId("01"));

        model.addAttribute("sEmpNm", map.get("sEmpNm"));
        model.addAttribute("sPost", map.get("sPost"));
        model.addAttribute("sDeptCd", map.get("sDeptCd"));

        Page<Tb201Dto> resultList = service020101.findAll(map.get("sEmpNm"), map.get("sPost"), map.get("sDeptCd"), pageable);
        model.addAttribute("dtos",resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "personDepart/020101";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("input");
        log.info("fstId = {}", map.get("fstId"));
        log.info("scdId = {}", map.get("scdId"));
        log.info("thdId = {}", map.get("thdId"));
        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", new GlobalConst());
        if(map.get("vseq").isEmpty()){
            model.addAttribute("dto",new Tb201Dto());
            return "personDepart/020101_input";
        }
        model.addAttribute("dto", service020101.findById(Integer.parseInt(map.get("vseq"))));
        return "personDepart/020101_input";
    }

    @PostMapping("/input")
    public String insert(@ModelAttribute Form020101 form, Model model, HttpServletRequest request) throws IOException {
        log.info("input");
        log.info("form = {}", form);
        if (service020101.insert(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Form020101 form, Model model, HttpServletRequest request){
        log.info("update");
        log.info("form = {}", form);
        if(service020101.update(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        if(service020101.delete(id)) model.addAttribute("msg", "정상적으로 삭제 되었습니다.");
        else model.addAttribute("msg", "문제가 발생하였습니다");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }
}
