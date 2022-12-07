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
import project.honey.personDepart.dto.PrintData020301;
import project.honey.personDepart.dto.Tb201Dto;
import project.honey.personDepart.service.Service020101;
import project.honey.personDepart.service.Service020102;
import project.honey.personDepart.service.Service020301;
import project.honey.system.service.Service990301;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/020301")
public class Controller020301 {

    private final Service020101 service020101;
    private final Service020102 service020102;
    private final Service020301 service020301;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;

    @GetMapping("")
    public String findAll(@RequestParam Map<String, String> map,
                          @ModelAttribute("menuId") MenuIdDto menuIdDto, Model model, Pageable pageable){
        log.info("재직증명서 메인");
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

        return "personDepart/020301";
    }

    @GetMapping("/popup/{empNo}")
    public String popUp(@PathVariable("empNo") String empNo, Model model){
        log.info("재직증명서 print");

        PrintData020301 data = service020301.getData(empNo);

        String nowYear = data.getDate().substring(0, 4);
        String nowMonth = data.getDate().substring(4, 6);
        String nowDay = data.getDate().substring(6, 8);
        String nowDate = nowYear + "-" + nowMonth + "-" + nowDay;

        String empDt = data.getEmpDt();
        String workDate = empDt.substring(0, 4) + "-" + empDt.substring(4, 6) + "-" + empDt.substring(6, 8);

        model.addAttribute("global", new GlobalConst());
        model.addAttribute("data", data);
        model.addAttribute("nowYear", nowYear);
        model.addAttribute("nowMonth", nowMonth);
        model.addAttribute("nowDay", nowDay);
        model.addAttribute("wordDate", workDate);
        model.addAttribute("nowDate", nowDate);

        return "personDepart/020301_prt";
    }
}
