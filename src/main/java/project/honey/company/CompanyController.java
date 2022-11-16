package project.honey.company;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.honey.comm.GlobalConst;
import project.honey.company.entity.Tb101;
import project.honey.company.repository.CompanyRepository;
import project.honey.company.service.CompanyService;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/info")
    public String companyInfo(Model model){
        CompanyInfoDto dto = companyService.findById(27);

        model.addAttribute("company", dto);
        model.addAttribute("title", GlobalConst.title);
        model.addAttribute("cssDir", GlobalConst.cssDir);
        model.addAttribute("jsDir", GlobalConst.jsDir);
        model.addAttribute("imgDir", GlobalConst.imgDir);

        return "company/companyInfo.html";
    }
//    @PostMapping("/info")
//    public String companySave(@ModelAttribute CompanyForm form) {
//
//    }

}
