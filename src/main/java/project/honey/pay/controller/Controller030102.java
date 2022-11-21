package project.honey.pay.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.function.ServerRequest;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.pay.dto.Tb301Dto;
import project.honey.pay.dto.Tb302ResultDto;
import project.honey.pay.service.Service030102;
import project.honey.system.service.Service990301;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/030102")
public class Controller030102 {

    private final Service030102 service030102;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;

    @GetMapping
    public String findAll(@RequestParam Map<String, String> map,
                          @ModelAttribute("menuId") MenuIdDto menuIdDto, Model model,
                          Pageable pageable) {

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "사원번호", "사원명", "입사일자", "직위/직급", "부서명",
                "지급액", "과세금액", "공제액", "실지급액"
        );
        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());


        Page<Tb302ResultDto> list = service030102.findAllByLeave(pageable, map.get("empNm"), map.get("post"), map.get("deptCd"));
        List<Tb302ResultDto> content = list.getContent();

        model.addAttribute("posts", service990301.findByFstId("01"));

        // 총합 구하기
        model.addAttribute("totalPayout", content.stream().mapToInt(Tb302ResultDto::getPayout).sum());
        model.addAttribute("totalTaxAmt", content.stream().mapToInt(Tb302ResultDto::getTaxAmt).sum());
        model.addAttribute("totalDeduction", content.stream().mapToInt(Tb302ResultDto::getDeduction).sum());
        model.addAttribute("totalActualPayment", content.stream().mapToInt(Tb302ResultDto::getActualPayment).sum());


        model.addAttribute("pageMaker", new PageMaker(pageable, list.getTotalElements()));
        model.addAttribute("list", content);

        return "pay/030102";
    }
}
