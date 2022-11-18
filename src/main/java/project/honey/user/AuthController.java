//package project.honey.user;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import project.honey.comm.GlobalConst;
//import project.honey.user.repository.UserRepository;
//
//import javax.servlet.http.HttpSession;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/auth")
//@Slf4j
//public class AuthController {
//
//    private final HttpSession session;
//    private final UserRepository userRepository;
//
//    // 로그인 폼 요청
//    @GetMapping("/login")
//    public String loginForm(Model model) {
//
//        model.addAttribute("title", GlobalConst.title);
//        model.addAttribute("cssDir", GlobalConst.cssDir);
//        model.addAttribute("jsDir", GlobalConst.jsDir);
//        model.addAttribute("imgDir", GlobalConst.imgDir);
//        return "login";
//
//    }
//
//    // 로그인
//    @PostMapping("/login")
//    public String login(LoginDto loginUser) {
//
//        log.info("loginUser : " + loginUser.toString());
//
//        // 진짜
////        if(!userRepository.existsByUserIdAndPasswd(loginUser.getUserId(), loginUser.getPasswd())){
////            return "redirect:/auth/login";
////        }
//
//        // 테스트용
//        if (!loginUser.getUserId().equals("hello") || !loginUser.getPasswd().equals("hello")) {
//            return "redirect:/auth/login";
//        }
//
//        // 세션에 ID 저장 후 main 이동
//        session.setAttribute("user", loginUser.getUserId());
//
//        return "redirect:/";
//    }
//
//    //로그아웃
//    @GetMapping("/logout")
//    public String logout(Model model) {
//
//        session.invalidate();
//
//        return "redirect:auth/login";
//    }
//}
