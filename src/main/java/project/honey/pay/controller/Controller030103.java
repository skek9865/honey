package project.honey.pay.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.pay.dto.*;
import project.honey.pay.service.Service030101;
import project.honey.pay.service.Service030102;
import project.honey.pay.service.Service030103;
import project.honey.personDepart.dto.Tb201Dto;
import project.honey.personDepart.service.Service020101;
import project.honey.personDepart.service.Service020102;
import project.honey.system.service.Service990301;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/030103")
public class Controller030103 {

    private final Service020101 service020101;
    private final Service020102 service020102;
    private final Service030101 service030101;
    private final Service030103 service030103;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final CodeToName codeToName;

    @GetMapping
    public String findAll(@RequestParam Map<String, String> map,
                          @ModelAttribute("menuId") MenuIdDto menuIdDto, Model model,
                          Pageable pageable) {
        //날짜 기본값 세팅
        String sPayDt = map.get("sPayDt");
        String sRPayDt = map.get("sRPayDt");
        if(sPayDt == null){
            sPayDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
            sRPayDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리","급여년월","지급일자", "사원번호", "사원명", "입사일자", "직위/직급", "부서명",
                "지급액", "과세금액", "공제액", "실지급액"
        );
        model.addAttribute("menus", menuMaker.getMenuId(1, "", "", ""));
        model.addAttribute("menuNm", menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles", titles);
        model.addAttribute("global", new GlobalConst());

        // 변환 데이터
        Map<String, String> deptMap = codeToName.dept();
        Map<String, String> postMap = codeToName.commonCode("01");

        Page<Tb303HomeDto> list = service030103.findAllByLeave(pageable,sPayDt.replaceAll("-", ""),
                map.get("sEmpNm"), map.get("sPost"), map.get("sDeptCd"));

        // 데이터 변환
        List<Tb303HomeDto> content = list.getContent().stream().peek(dto -> {
            dto.setPost(postMap.get(dto.getPost()));
            dto.setDeptNm(deptMap.get(dto.getDeptNm()));
        }).collect(Collectors.toList());

        model.addAttribute("posts", service990301.findByFstId("01"));
        model.addAttribute("depts", service020102.findAllDept());

        // 총합 구하기
        model.addAttribute("totalPayout", content.stream().mapToInt(Tb303HomeDto::getPayout).sum());
        model.addAttribute("totalTaxAmt", content.stream().mapToInt(Tb303HomeDto::getTaxAmt).sum());
        model.addAttribute("totalDeduction", content.stream().mapToInt(Tb303HomeDto::getDeduction).sum());
        model.addAttribute("totalActualPayment", content.stream().mapToInt(Tb303HomeDto::getActualPayment).sum());

        //검색조건 유지
        model.addAttribute("sPayDt", sPayDt);
        model.addAttribute("sRPayDt", sRPayDt);
        model.addAttribute("sEmpNm", map.get("sEmpNm"));
        model.addAttribute("sPost", map.get("sPost"));
        model.addAttribute("sDeptCd", map.get("sDeptCd"));


        model.addAttribute("pageMaker", new PageMaker(pageable, list.getTotalElements()));
        model.addAttribute("list", content);

        return "pay/030103";
    }

    //급여항목 등록
    @PostMapping("/insert")
    public String insert(Tb303Dto dto, HttpServletRequest request, Model model) {
        log.info("dto = {}", dto);
        model.addAttribute("msg", service030103.insert(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(Tb303Dto dto, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service030103.update(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{seq}")
    public String delete(@PathVariable Integer seq, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service030103.delete(seq) != null ? "정상적으로 삭제 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    //급여항목 추가(단건)
    @PostMapping("/pitemesave/{empNo}")
    public String pitemeSaveOne(@PathVariable String empNo,String sPayDt, String sRPayDt, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service030103.pitemeSaveOne(empNo, sPayDt, sRPayDt) != null ? "정상적으로 계산 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    //급여항목 삭제(단건)
    @PostMapping("/pitemedel/{empNo}")
    public String pitemeDelOne(@PathVariable String empNo, String sPayDt, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service030103.pitemeDelOne(empNo, sPayDt) != null ? "정상적으로 삭제 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    //급여항목 추가
    @PostMapping("/pitemesave")
    public String pitemeSave(String sPayDt,String sRPayDt, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service030103.pitemeSave(sPayDt, sRPayDt).equals("ok") ? "정상적으로 계산 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    //급여항목 삭제
    @PostMapping("/pitemedel")
    public String pitemedel(String sPayDt,HttpServletRequest request, Model model) {
        model.addAttribute("msg", service030103.pitemeDel(sPayDt).equals("ok") ? "정상적으로 삭제 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    //이전달급여 추가(단건)
    @PostMapping("/payload/{empNo}")
    public String payloadOne(@PathVariable String empNo,String sPayDt, String sRPayDt, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service030103.payloadOne(empNo, sPayDt, sRPayDt) != null ? "정상적으로 계산 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    //이전달급여 추가
    @PostMapping("/payload")
    public String payload(String sPayDt, String sRPayDt, HttpServletRequest request, Model model) {
        model.addAttribute("msg", service030103.payload(sPayDt, sRPayDt) != null ? "정상적으로 계산 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/popup/{empNo}")
    public String popup(@PathVariable String empNo, String payDt,String rPayDt, MenuIdDto menuIdDto, Model model) {
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("menuNm", menuMaker.getMenuNm(menuIdDto));
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "공제/지급", "과세여부", "급여항목", "금액"
        );

        // 변환 데이터
        Map<String, String> itemMap = codeToName.item();
        Map<String, String> itemDivMap = codeToName.commonCode("05");
        Map<String, String> postMap = codeToName.commonCode("01");

        List<Tb303PopupDto> list = service030103.findAll(empNo, payDt).stream().peek(dto -> {
            dto.setItemCd(itemMap.get(dto.getItemCd()));
            dto.setItemDiv(itemDivMap.get(dto.getItemDiv()));
        }).collect(Collectors.toList());;

        Tb201Dto empDto = service020101.findByEmpNo(empNo);
        Tb201Dto emp = Tb201Dto.builder()
                .empNm(empDto.getEmpNm())
                .post(postMap.get(empDto.getPost()))
                .build();
        model.addAttribute("dtos", list);

        // 상단에 출력할 데이터
        model.addAttribute("emp", emp);
        model.addAttribute("payDt", payDt);
        model.addAttribute("rPayDt", rPayDt);

        model.addAttribute("titles", titles);
        model.addAttribute("totalPayAmt", list.stream().mapToDouble(Tb303PopupDto::getPayAmt).sum());

        return "pay/030103_1";
    }

    @GetMapping("/input")
    public String input(Model model,String empNo,String payDt, String rPayDt,
                        String action, Integer id){
        log.info("input");
        log.info("action = {}", action);
        log.info("id = {}", id);
        model.addAttribute("dto",
                id != null
                        ? service030103.findById(id)
                        : new Tb303Dto());
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("action", action);
        model.addAttribute("empNo", empNo);
        model.addAttribute("payDt", payDt);
        model.addAttribute("rPayDt", rPayDt);
        model.addAttribute("codes", service030101.findAllItem());
        return "pay/030103_1_input";
    }

}
