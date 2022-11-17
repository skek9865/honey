package project.honey.personDepart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.honey.comm.GlobalConst;
import project.honey.comm.menu.MenuIdDto;
import project.honey.personDepart.dto.Tb201Dto;
import project.honey.personDepart.service.Tb201Service;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/020101")
public class Controller020101 {

    private final Tb201Service service;

    @GetMapping("/")
    public String main(@RequestParam Map<String, String> map, Model model){
        log.info("empNm = {}, postCd = {}, deptCd = {}" ,map.get("empNm"), map.get("postCd"), map.get("deptCd"));
        List<Tb201Dto> resultList = service.findAll(map.get("empNm"), map.get("postCd"), map.get("deptCd"));
        model.addAttribute("dto",resultList);
        model.addAttribute("global", new GlobalConst());
        return "personDepart/020101";
    }

    @GetMapping("/input")
    public String input(Model model){
        model.addAttribute("dto",new Tb201Dto());
        model.addAttribute("global", new GlobalConst());
        return "personDepart/020101_input";
    }
}
