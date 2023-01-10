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
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.pay.dto.Tb303HomeDto;
import project.honey.pay.dto.PayrollDto;
import project.honey.pay.dto.TotalPayrollDto;
import project.honey.pay.service.Service030101;
import project.honey.pay.service.Service030103;
import project.honey.pay.service.Service030104;
import project.honey.personDepart.service.Service020101;
import project.honey.personDepart.service.Service020102;
import project.honey.system.dto.CodeDto;
import project.honey.system.service.Service990301;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/030104")
public class Controller030104 {
    private final Service020102 service020102;
    private final Service030101 service030101;
    private final Service030104 service030104;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final CodeToName codeToName;

    @GetMapping
    public String findAll(@RequestParam Map<String, String> map,
                          @ModelAttribute("menuId") MenuIdDto menuIdDto, Model model,
                          Pageable pageable) {
        //날짜 기본값 세팅
        String sPayDt = map.get("sPayDt");
        if (sPayDt == null) {
            sPayDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        }

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);

        // 급여대장 타이틀 구하기
        List<String> titles = GlobalMethod.makeTitle("순번", "입사일", "사원", "부서");
        List<String> useTitles = service030101.findAllByUseItemNm();
        titles.addAll(useTitles);
        titles.addAll(List.of(new String[]{"지급총액", "공제총액", "실지급액"}));

        model.addAttribute("menus", menuMaker.getMenuId(1, "", "", ""));
        model.addAttribute("menuNm", menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles", titles);
        model.addAttribute("global", new GlobalConst());


        // 급여대장 부서 -> 직급 변환
        List<CodeDto> depts = service020102.findAllDept();
        Map<String, String> deptMap = codeToName.dept();


        // 급여대장
        Page<PayrollDto> pagingList = service030104.findAll(pageable, sPayDt.replaceAll("-", ""),
                map.get("sEmpNm"), map.get("sPost"), map.get("sDeptCd"));
        List<PayrollDto> list = pagingList.getContent()
                .stream().peek(m -> {
                    m.setPost(deptMap.get(m.getPost()));
                    m.setEmpDt(m.getEmpDt().substring(0, 4) + "-" + m.getEmpDt().substring(4, 6) + "-" + m.getEmpDt().substring(6));
                })
                .collect(Collectors.toList());

        model.addAttribute("posts", service990301.findByFstId("01"));
        model.addAttribute("depts", depts);

        // 총합 구하기
        TotalPayrollDto totalPayrollDto = TotalPayrollDto.of(list, useTitles);
        log.info("totalPayroll = {}", totalPayrollDto);
        model.addAttribute("totals", totalPayrollDto);

        //검색조건 유지
        model.addAttribute("sPayDt", sPayDt);
        model.addAttribute("sEmpNm", map.get("sEmpNm"));
        model.addAttribute("sPost", map.get("sPost"));
        model.addAttribute("sDeptCd", map.get("sDeptCd"));

        model.addAttribute("pageMaker", new PageMaker(pageable, pagingList.getTotalElements()));
        model.addAttribute("list", list);

        return "pay/030104";
    }

    @GetMapping("/print")
    public String print(@RequestParam Map<String, String> map, Model model,
                        Pageable pageable) {
        //날짜 기본값 세팅
        String sPayDt = map.get("sPayDt");
        if (sPayDt == null) {
            sPayDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        }

        log.info("pageable : " + pageable);

        // 급여대장 타이틀 구하기
        List<String> titles = GlobalMethod.makeTitle("순번", "입사일", "사원", "부서");
        List<String> useTitles = service030101.findAllByUseItemNm();
        titles.addAll(useTitles);
        titles.addAll(List.of(new String[]{"지급총액", "공제총액", "실지급액"}));

        model.addAttribute("titles", titles);
        model.addAttribute("global", new GlobalConst());


        // 급여대장 부서 -> 직급 변환
        List<CodeDto> depts = service020102.findAllDept();
        Map<String, String> deptMap = codeToName.dept();


        // 급여대장
        Page<PayrollDto> pagingList = service030104.findAll(pageable, sPayDt.replaceAll("-", ""),
                map.get("sEmpNm"), map.get("sPost"), map.get("sDeptCd"));
        List<PayrollDto> list = pagingList.getContent()
                .stream().peek(m -> {
                    m.setPost(deptMap.get(m.getPost()));
                    m.setEmpDt(m.getEmpDt().substring(0, 4) + "-" + m.getEmpDt().substring(4, 6) + "-" + m.getEmpDt().substring(6));
                })
                .collect(Collectors.toList());

        model.addAttribute("posts", service990301.findByFstId("01"));
        model.addAttribute("depts", depts);

        // 총합 구하기
        TotalPayrollDto totalPayrollDto = TotalPayrollDto.of(list, useTitles);
        log.info("totalPayroll = {}", totalPayrollDto);
        model.addAttribute("totals", totalPayrollDto);

        //검색조건 유지
        model.addAttribute("sPayDt", sPayDt);
        model.addAttribute("year", sPayDt.split("-")[0]);
        model.addAttribute("month", sPayDt.split("-")[1]);
        model.addAttribute("sEmpNm", map.get("sEmpNm"));
        model.addAttribute("sPost", map.get("sPost"));
        model.addAttribute("sDeptCd", map.get("sDeptCd"));

        model.addAttribute("pageMaker", new PageMaker(pageable, pagingList.getTotalElements()));
        model.addAttribute("list", list);

        return "pay/030104_prt";
    }
}
