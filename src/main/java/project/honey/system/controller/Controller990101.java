package project.honey.system.controller;

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
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.comm.PageMaker;
import project.honey.system.service.Service990101;
import project.honey.system.dto.Tb901Dto;

import java.util.List;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/990101")
public class Controller990101 {

    private final Service990101 service990101;
    private final MenuMaker menuMaker;

    // 사용자 전체 조회
    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model, Pageable pageable) {

        log.info("pageable : " + pageable);
        log.info("menuIdDto : {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "아이디", "비밀번호", "사용자이름", "전화번호",
                "모바일", "Email","사용자그룹", "사용여부", "사원여부", "사원명", "등록일자"
        );

        // 임시
        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb901Dto> users = service990101.findAll(pageable);
        model.addAttribute("pageMaker", new PageMaker(pageable, users.getTotalElements()));
        model.addAttribute("users", users.getContent());

        return "system/990101";
    }

    // 사용자 단건 조회
    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<Tb901Dto> findAll(@PathVariable String userId) {
        log.info("userId : " + userId);
        return new ResponseEntity<>(service990101.findById(userId), HttpStatus.OK);
    }

//    // 사용자 저장
//    @PostMapping("/insert")
//    @ResponseBody
//    public ResponseEntity<String> insert(Tb901Dto dto) {
//        log.info("user : " + dto);
//        service990101.insert(dto);
//        return new ResponseEntity<>("ok", HttpStatus.OK);
//    }

    // 사용자 저장
    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb901Dto dto, Pageable pageable ,
                         @ModelAttribute MenuIdDto menuIdDto, RedirectAttributes redirectAttributes) {
        log.info("user : " + dto);
        service990101.insert(dto);

        addRedirect(pageable, menuIdDto, redirectAttributes);
        return "redirect:/990101";
    }

    // 사용자 수정
    @PostMapping("/update")
    public String update(@ModelAttribute Tb901Dto dto, Pageable pageable ,
                         @ModelAttribute MenuIdDto menuIdDto, RedirectAttributes redirectAttributes) {

        log.info("user : " + dto);
        service990101.update(dto);

        addRedirect(pageable, menuIdDto, redirectAttributes);
        return "redirect:/990101";
    }

    private void addRedirect(Pageable pageable, @ModelAttribute MenuIdDto menuIdDto, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("fstId", menuIdDto.getFstId());
        redirectAttributes.addAttribute("scdId", menuIdDto.getScdId());
        redirectAttributes.addAttribute("thdId", menuIdDto.getThdId());
        redirectAttributes.addAttribute("page", pageable.getPageNumber()+1);
        redirectAttributes.addAttribute("size", pageable.getPageSize());
    }


    // 사용자 삭제
    @GetMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> delete(String id) {

        log.info("id : " + id);
        service990101.delete(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @GetMapping("/input")
    public String input(Model model, String id){
        log.info("input");
        log.info("id = {}", id);
        model.addAttribute("dto",
                id != null
                        ? service990101.findById(id)
                        : new Tb901Dto());
        model.addAttribute("global", new GlobalConst());
        return "system/990101_input";
    }
}
