package project.honey.comm.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.honey.comm.GlobalConst;
import project.honey.system.entity.Tb902;
import project.honey.system.repository.Tb901Repository;
import project.honey.system.repository.Tb902Repository;
import project.honey.system.service.Service990102;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final HttpSession session;
    private final Tb901Repository tb901Repository;
    private final Service990102 service990102;

    // 로그인 폼 요청
    @GetMapping("/login")
    public String loginForm(Model model) {

        model.addAttribute("global", new GlobalConst());

        return "login";

    }

    // 로그인
    @PostMapping("/login")
    public String login(LoginDto loginUser, HttpServletRequest request) {

        log.info("loginUser : " + loginUser.toString());

        if (!tb901Repository.existsByUserIdAndPasswd(loginUser.getUserId(), loginUser.getPasswd())) {
            return "redirect:/auth/login";
        }

        session.setAttribute("user", loginUser.getUserId());

        //로그인 이력 저장
        service990102.insertHistory(request, loginUser.getUserId());

        return "redirect:/";
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout() {

        session.invalidate();

        return "redirect:auth/login";
    }
}
