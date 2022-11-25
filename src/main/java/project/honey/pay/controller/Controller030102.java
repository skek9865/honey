package project.honey.pay.controller;

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
import project.honey.pay.dto.Tb302Dto;
import project.honey.pay.dto.Tb302HomeDto;
import project.honey.pay.dto.Tb302PopupDto;
import project.honey.pay.service.Service030101;
import project.honey.pay.service.Service030102;
import project.honey.personDepart.service.Service020101;
import project.honey.personDepart.service.Service020201;
import project.honey.system.service.Service990301;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/030102")
public class Controller030102 {

    private final Service020101 service020101;
    private final Service030101 service030101;
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
        model.addAttribute("menus", menuMaker.getMenuId(1, "", "", ""));
        model.addAttribute("menuNm", menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles", titles);
        model.addAttribute("global", new GlobalConst());


        Page<Tb302HomeDto> list = service030102.findAllByLeave(pageable, map.get("sEmpNm"), map.get("sPost"), map.get("sDeptCd"));
        List<Tb302HomeDto> content = list.getContent();

        model.addAttribute("posts", service990301.findByFstId("01"));

        // 총합 구하기
        model.addAttribute("totalPayout", content.stream().mapToInt(Tb302HomeDto::getPayout).sum());
        model.addAttribute("totalTaxAmt", content.stream().mapToInt(Tb302HomeDto::getTaxAmt).sum());
        model.addAttribute("totalDeduction", content.stream().mapToInt(Tb302HomeDto::getDeduction).sum());
        model.addAttribute("totalActualPayment", content.stream().mapToInt(Tb302HomeDto::getActualPayment).sum());

        //검색조건 유지
        model.addAttribute("sEmpNm", map.get("sEmpNm"));
        model.addAttribute("sPost", map.get("sPost"));
        model.addAttribute("sDeptCd", map.get("sDeptCd"));


        model.addAttribute("pageMaker", new PageMaker(pageable, list.getTotalElements()));
        model.addAttribute("list", content);

        return "pay/030102";
    }

    //급여항목 등록
    @PostMapping("/insert")
    public String insert(Tb302Dto dto, HttpServletRequest request, Model model) {
        log.info("dto = {}", dto);
        model.addAttribute("msg", service030102.insert(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(Tb302Dto dto, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service030102.update(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{seq}")
    public String update(@PathVariable Integer seq, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service030102.delete(seq) != null ? "정상적으로 삭제 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/popup/{empNo}")
    public String popup(@PathVariable String empNo, MenuIdDto menuIdDto, Model model) {
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("menuNm", menuMaker.getMenuNm(menuIdDto));
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "공제/지급", "과세여부", "급여항목", "금액"
        );

        List<Tb302PopupDto> list = service030102.findAll(empNo);
        model.addAttribute("dtos", list);
        model.addAttribute("emp", service020101.findByEmpNo(empNo));

        model.addAttribute("titles", titles);
        model.addAttribute("totalPayAmt", list.stream().mapToDouble(Tb302PopupDto::getPayAmt).sum());

        return "pay/030102_1";
    }

    //급여항목 추가(단건)
    @PostMapping("/pitemesave/{empNo}")
    public String pitemeSaveOne(@PathVariable String empNo, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service030102.pitemeSaveOne(empNo) != null ? "정상적으로 계산 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    //급여항목 삭제(단건)
    @PostMapping("/pitemedel/{empNo}")
    public String pitemeDelOne(@PathVariable String empNo, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service030102.pitemeDelOne(empNo) != null ? "정상적으로 삭제 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    //급여항목 추가
    @PostMapping("/pitemesave")
    public String pitemeSave(HttpServletRequest request, Model model) {
        model.addAttribute("msg", service030102.pitemeSave().equals("ok") ? "정상적으로 계산 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    //급여항목 삭제
    @PostMapping("/pitemedel")
    public String pitemedel(HttpServletRequest request, Model model) {
        model.addAttribute("msg", service030102.pitemeDel().equals("ok") ? "정상적으로 삭제 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/input")
    public String input(Model model,String empNo, String action, Integer id){
        log.info("input");
        log.info("action = {}", action);
        log.info("id = {}", id);
        model.addAttribute("dto",
                id != null
                        ? service030102.findById(id)
                        : new Tb302Dto());
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("action", action);
        model.addAttribute("empNo", empNo);
        model.addAttribute("codes", service030101.findAllItem());
        return "pay/030102_1_input";
    }
}

