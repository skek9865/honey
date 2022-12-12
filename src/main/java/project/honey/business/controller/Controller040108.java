package project.honey.business.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.honey.business.dto.Tb402Dto;
import project.honey.business.form.Tb402Form;
import project.honey.business.service.*;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/040108")
public class Controller040108 {

    private final Service040108 service040108;
    private final Service040107 service040107;
    private final MenuMaker menuMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          Pageable pageable, Model model){

        log.info("거래처특별단가 메인");
        log.info("menuId = {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "거래처코드", "상호(이름)", "대표자명",
                "담당자", "영업사원", "거래처그룹1", "거래처그룹2", "영업단가그룹", "구매단가그룹"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb402Dto> resultList = service040108.findAllByDsl(pageable);
        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "business/040108";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("거래처특별단가 input");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));
        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("specialCodes", service040107.findAllBySelect());
        if(map.get("vseq").isEmpty()){
            model.addAttribute("dto",new Tb402Dto());
            return "business/040108_input";
        }
        model.addAttribute("dto", service040108.findById(Integer.parseInt(map.get("vseq"))));
        return "business/040108_input";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb402Form form, Model model, HttpServletRequest request){
        log.info("거래처특별단가 update");
        log.info("form = {}", form);
        if(service040108.update(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }
}
