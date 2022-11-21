package project.honey.personDepart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.personDepart.dto.Form020101;
import project.honey.personDepart.dto.Tb201Dto;
import project.honey.personDepart.service.Service020101;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/020101")
public class Controller020101 {

    private final Service020101 service;
    private final MenuMaker menuMaker;

    @GetMapping("")
    public String findAll(@RequestParam Map<String, String> map,
                          @ModelAttribute("menuId")MenuIdDto menuIdDto, Model model, Pageable pageable){
        log.info("empNm = {}, postCd = {}, deptCd = {}" ,map.get("empNm"), map.get("postCd"), map.get("deptCd"));
        log.info("menuId = {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "사원번호", "사원명", "입사일자", "직위/직급",
                "전화번호", "모바일", "Email", "부서명", "업무코드"
        );
        Page<Tb201Dto> resultList = service.findAll(map.get("empNm"), map.get("postCd"), map.get("deptCd"), pageable);
        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("dtos",resultList);
        model.addAttribute("global", new GlobalConst());
        return "personDepart/020101";
    }

    @GetMapping("/input")
    public String read(@RequestParam Map<String, String> map, Model model){
        log.info("input");
        log.info("fstId = {}", map.get("fstId"));
        log.info("scdId = {}", map.get("scdId"));
        log.info("thdId = {}", map.get("thdId"));
        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("dto",new Tb201Dto());
        model.addAttribute("global", new GlobalConst());
        return "personDepart/020101_input";
    }

    @PostMapping("/input")
    public String insert(@ModelAttribute Form020101 form,@RequestParam Map<String,String> map,
                         Pageable pageable,RedirectAttributes redirectAttributes) throws IOException {
        log.info("input");
        log.info("form = {}", form);
        service.insert(form);
        redirectAttributes.addAttribute("fstId",map.get("fstId"));
        redirectAttributes.addAttribute("scdId",map.get("scdId"));
        redirectAttributes.addAttribute("thdId",map.get("thdId"));
        redirectAttributes.addAttribute("page",pageable.getPageNumber());
        redirectAttributes.addAttribute("size",pageable.getPageSize());
        redirectAttributes.addAttribute("sEmpNm",map.get("sEmpNm"));
        redirectAttributes.addAttribute("sPost",map.get("sPost"));
        redirectAttributes.addAttribute("sDeptCd",map.get("sDeptCd"));
        return "redirect:/020101";
    }

    @GetMapping("update")
    public String update(@RequestParam Map<String, String> map, Model model){
        log.info("update");
        log.info("fstId = {}", map.get("fstId"));
        log.info("scdId = {}", map.get("scdId"));
        log.info("thdId = {}", map.get("thdId"));
        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("dto",new Tb201Dto());
        model.addAttribute("global", new GlobalConst());
        return "personDepart/020101_input";
    }
}
