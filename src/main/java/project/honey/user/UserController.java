package project.honey.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.honey.comm.menu.MenuMaker;

import javax.servlet.http.HttpSession;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/990101")
public class UserController {

    private final UserService userService;
    private final MenuMaker menuMaker;

    // 사용자 전체 조회
    @GetMapping
    public String findAll(Model model, Pageable pageable) {

        log.info("pageable : " + pageable);

        // 임시
        model.addAttribute("menus", menuMaker.getMenuId(1,"00","00",""));
        model.addAttribute("title", " 사용자관리 (990101)");
        model.addAttribute("path", "시스템관리 > 사용자관리 > 사용자관리");

        Page<UserDto> users = userService.findAll(pageable);
        model.addAttribute("pageMaker", new PageMaker(pageable, users.getTotalElements()));
        model.addAttribute("users", users.getContent());

        return "990101";
    }

    // 사용자 단건 조회
    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<UserDto> findAll(@PathVariable String userId) {
        log.info("userId : " + userId);
        return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
    }

    // 사용자 저장
    @PostMapping("/insert")
    public String insert(UserDto dto) {

        log.info("user : " + dto);
        userService.insert(dto);
        return "redirect:/990101";
    }

    // 사용자 수정
    @PostMapping("/update")
    public String update(UserDto dto) {

        log.info("user : " + dto);
        userService.insert(dto);
        return "redirect:/990101";
    }

    // 사용자 삭제
    @GetMapping("/delete")
    public String delete(String userId) {

        log.info("userId : " + userId);
        userService.delete(userId);
        return "redirect:/990101";
    }


}
