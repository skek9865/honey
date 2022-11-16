package project.honey.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.honey.comm.GlobalConst;

import javax.servlet.http.HttpSession;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/990101")
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    // 사용자 정보
    @GetMapping
    public String userList(Model model, Pageable pageable) {

        log.info("pageable : " + pageable);
        model.addAttribute("users", userService.userList(pageable));

        return "990101";
    }

    // 사용자 저장
    @PostMapping("/register")
    public String insertUser(UserDto dto) {

        log.info("user : " + dto);

        userService.insertUser(dto);

        return "redirect:/990101";
    }

    // 사용자 삭제
    @GetMapping("/delete")
    public String deleteUser(String userId) {

        log.info("userId : " + userId);

        userService.deleteUser(userId);

        return "redirect:/990101";
    }


}
