package project.honey.company.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class Controller010201 {

    private final MenuMaker menuMaker;

    @GetMapping("/010201")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model, Pageable pageable){
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "사원번호", "사원명", "입사일자", "직위/직급",
                "전화번호", "모바일", "Email", "부서명", "업무코드"
        );
        model.addAttribute("titles",titles);

        return "company/010201.html";
    }
}
