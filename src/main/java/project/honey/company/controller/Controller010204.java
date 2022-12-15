package project.honey.company.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.company.dto.Tb104Dto;
import project.honey.company.dto.Tb105Dto;
import project.honey.company.service.Service010204;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/010204")
public class Controller010204 {

    private final MenuMaker menuMaker;
    private final Service010204 service010204;
    @GetMapping()
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model, Pageable pageable){

        Page<Tb105Dto> resultList = service010204.findAll(pageable);
        List<Tb105Dto> content = resultList.getContent();
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "대출명", "계좌번호", "신규일", "만기일",
                "약정한도", "납입금", "대출잔액", "이자일", "금리", "비고"
        );

        Integer totalLimitamt = service010204.getTotalLimitamt(content);
        Integer totalInstamt = service010204.getTotalInstamt(content);
        Integer totalRest = totalLimitamt-totalInstamt;

        //footer (총 한도)
        List<String> footer = GlobalMethod.makeFooter(
                "","","","","","",
                totalLimitamt.toString(),totalInstamt.toString(), totalRest.toString(),"","",""
        );

        model.addAttribute("global", new GlobalConst());
        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));

        model.addAttribute("titles",titles);
        model.addAttribute("dtos",resultList);
        model.addAttribute("footer",footer);

        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "company/010204";
    }
}
