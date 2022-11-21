package project.honey.pay.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.InputMenuIdDto;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.pay.dto.Tb301Dto;
import project.honey.pay.entity.Tb301;
import project.honey.pay.service.Service030101;
import project.honey.system.dto.Tb906Dto;
import project.honey.system.entity.Tb903;
import project.honey.system.service.Service990301;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/030101")
public class Controller030101 {

    private final Service030101 service030101;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;

    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model,
                          Pageable pageable) {

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "공제/지급구분", "과세여부", "항목코드", "항목명", "세제율", "사용여부"
        );
        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb301Dto> list = service030101.findAll(pageable);
        model.addAttribute("pageMaker", new PageMaker(pageable, list.getTotalElements()));
        model.addAttribute("list", list.getContent());

        return "pay/030101";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb301Dto dto, Pageable pageable ,
                         @ModelAttribute MenuIdDto menuIdDto, RedirectAttributes redirectAttributes) {
        log.info("dto : {}", dto);
        service030101.insert(dto);

        addRedirect(pageable, menuIdDto, redirectAttributes);
        return "redirect:/030101";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb301Dto dto, Pageable pageable ,
                         @ModelAttribute MenuIdDto menuIdDto, RedirectAttributes redirectAttributes) {

        service030101.update(dto);

        addRedirect(pageable, menuIdDto, redirectAttributes);
        return "redirect:/030101";
    }

    private void addRedirect(Pageable pageable, @ModelAttribute MenuIdDto menuIdDto,
                             RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("fstId", menuIdDto.getFstId());
        redirectAttributes.addAttribute("scdId", menuIdDto.getScdId());
        redirectAttributes.addAttribute("thdId", menuIdDto.getThdId());
        redirectAttributes.addAttribute("page", pageable.getPageNumber()+1);
        redirectAttributes.addAttribute("size", pageable.getPageSize());
    }

    @GetMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> delete(Integer id) {

        log.info("id : " + id);
        service030101.delete(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @GetMapping("/input")
    public String input(Model model, Integer id){
        log.info("input");
        log.info("id = {}", id);
        model.addAttribute("dto",
                id != null
                        ? service030101.findById(id)
                        : new Tb301Dto());
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("codes", service990301.findByFstId("05"));
        return "pay/030101_input";
    }
}
