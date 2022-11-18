package project.honey.personDepart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.honey.comm.GlobalConst;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.comm.menu.QueryParam;
import project.honey.personDepart.dto.Tb201Dto;
import project.honey.personDepart.service.Tb201Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/020101")
public class Controller020101 {

    private final Tb201Service service;
    private final MenuMaker menuMaker;

    @GetMapping("")
    public String main(@RequestParam Map<String, String> map,
                       @ModelAttribute("menuId") QueryParam queryParam, Model model){
        log.info("empNm = {}, postCd = {}, deptCd = {}" ,map.get("empNm"), map.get("postCd"), map.get("deptCd"));
        List<String> titles = makeTitle(
                "순번", "관리", "사원번호", "사원명", "입사일자", "직위/직급",
                "전화번호", "모바일", "Email", "부서명", "업무코드"
        );
        List<Tb201Dto> resultList = service.findAll(map.get("empNm"), map.get("postCd"), map.get("deptCd"));
        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(queryParam));
        model.addAttribute("titles",titles);
        model.addAttribute("dtos",resultList);
        model.addAttribute("global", new GlobalConst());
        return "personDepart/020101";
    }

    @GetMapping("/input")
    public String input(Model model){
        log.info("input");
        model.addAttribute("dto",new Tb201Dto());
        model.addAttribute("global", new GlobalConst());
        return "personDepart/020101_input";
    }

    private List<String> makeTitle(String ... titles){
        List<String> result  = new ArrayList<>();
        for(String title : titles) result.add(title);
        return result;
    }
}
