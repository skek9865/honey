package project.honey.company.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.company.dto.Tb102Dto;
import project.honey.company.service.Service010201;
import project.honey.personDepart.dto.Tb201Dto;
import project.honey.system.service.Service990301;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class Controller010201 {

    private final MenuMaker menuMaker;
    private final Service010201 service010201;
    private final Service990301 service990301;

    @GetMapping("/010201")
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

    @GetMapping("/010201/input")
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
        model.addAttribute("bankNmCodes",service990301.findByFstId("04"));
        if(map.get("vseq").isEmpty()){
            model.addAttribute("dto",new Tb102Dto());
            return "company/010201_input";
        }
        model.addAttribute("dto", service010201.findById(Integer.parseInt(map.get("vseq"))));
        return "company/010201_input";
    }
}
