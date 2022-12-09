package project.honey.company.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.company.dto.Tb102Dto;
import project.honey.company.dto.Tb103Dto;
import project.honey.company.service.Service010202;
import project.honey.personDepart.repository.Tb201Repository;
import project.honey.personDepart.service.Service020101;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/010202")
@Slf4j
public class Controller010202 {

    private final MenuMaker menuMaker;
    private final Service010202 service010202;
    private final Tb201Repository tb201Repository;

    @GetMapping()
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model, Pageable pageable){

        Page<Tb103Dto> resultList = service010202.findAll(pageable);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "인증서명", "만료일", "사용용도", "보관형태",
                "관리자", "사용여부", "비고"
        );

        model.addAttribute("global", new GlobalConst());
        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));

        model.addAttribute("titles",titles);
        model.addAttribute("dtos",resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "company/010202";
    }
    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("fstId = {}", map.get("fstId"));
        log.info("scdId = {}", map.get("scdId"));
        log.info("thdId = {}", map.get("thdId"));
        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("empList",tb201Repository.findAll().stream().filter(e -> !StringUtils.hasText(e.getLeaveRs())).collect(Collectors.toList()));
        if(map.get("vseq").isEmpty()){
            model.addAttribute("dto",new Tb103Dto());
            return "company/010202_input";
        }
        model.addAttribute("dto", service010202.findById(Integer.parseInt(map.get("vseq"))));
        return "company/010202_input";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb103Dto dto, Model model, HttpServletRequest request){
        log.info("dto : {}", dto);

        model.addAttribute("msg", service010202.insert(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));

        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb103Dto dto, Model model, HttpServletRequest request){
        log.info("dto : {}", dto);

        model.addAttribute("msg", service010202.update(dto) != null ? "정상적으로 수정 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));

        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model ,HttpServletRequest request) {

        log.info("id : " + id);

        model.addAttribute("msg", service010202.delete(id) != null ? "정상적으로 삭제 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }
}