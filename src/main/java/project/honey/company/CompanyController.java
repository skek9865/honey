package project.honey.company;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.honey.comm.GlobalConst;
import project.honey.company.entity.Tb101;
import project.honey.company.repository.CompanyRepository;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/company")
public class CompanyController {

    private final CompanyRepository companyRepository;

    @GetMapping("/info")
    public String companyInfo(Model model){
        Tb101 tb101 = companyRepository.findById(27).get();

        model.addAttribute("company", tb101);
        model.addAttribute("title", GlobalConst.title);
        model.addAttribute("cssDir", GlobalConst.cssDir);
        model.addAttribute("jsDir", GlobalConst.jsDir);
        model.addAttribute("imgDir", GlobalConst.imgDir);

        return "company/companyInfo.html";
    }

}
