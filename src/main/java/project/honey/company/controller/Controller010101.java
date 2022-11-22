package project.honey.company.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import project.honey.comm.GlobalConst;
import project.honey.comm.UploadService;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.company.dto.CompanyForm;
import project.honey.company.dto.Tb101Dto;
import project.honey.company.service.Service010101;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("")
public class Controller010101 {

    private final Service010101 service010101;
    private final MenuMaker menuMaker;
    private final String fileDir = "C:\\JAVA\\honey\\src\\main\\resources\\static\\images\\corp";

    @GetMapping("/010101")
    public String companyInfo(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model){
        Tb101Dto dto = service010101.findById(27);

        model.addAttribute("company", dto);
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("form", new CompanyForm());

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));

        return "company/010101";
    }
    @PostMapping("/010101")
    public RedirectView companySave(@ModelAttribute("form") CompanyForm form) {
        log.info("form = "+form);
        if(!form.getLogonm().isEmpty()) {
            MultipartFile logoFile = form.getLogonm();
            UploadService.uploadFile(logoFile,fileDir);
        }

        if(!form.getStampnm().isEmpty()) {
            MultipartFile stampFile = form.getStampnm();
            UploadService.uploadFile(stampFile,fileDir);
        }

        service010101.save(form);

        return new RedirectView("/010101");
    }

}
