package project.honey.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.honey.GlobalConst;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String main(Model model){
        model.addAttribute("title", GlobalConst.title);
        model.addAttribute("cssDir", GlobalConst.cssDir);
        model.addAttribute("jsDir", GlobalConst.jsDir);
        model.addAttribute("imgDir", GlobalConst.imgDir);
        return "main";
    }
}
