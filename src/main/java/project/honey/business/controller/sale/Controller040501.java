package project.honey.business.controller.sale;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.honey.business.dto.basic.Tb401Dto;
import project.honey.business.dto.manage.Tb410MainDto;
import project.honey.business.dto.sale.Tb417Dto;
import project.honey.business.form.basic.Tb401Form;
import project.honey.business.form.sale.Tb417Form;
import project.honey.business.service.sale.Service040501;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.company.service.Service010201;
import project.honey.personDepart.service.Service020101;
import project.honey.system.service.Service990301;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/040501")
public class Controller040501 {

    private final Service040501 service040501;
    private final Service010201 service010201;
    private final Service020101 service020101;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model, Pageable pageable){
        log.info("입출금관리 메인");
        log.info("menuId = {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "입출금일자", "입출금순번", "입출/구분", "입출타입",
                "계좌", "매출액", "입출금액", "거래처명", "사원명", "적요", "참조"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb417Dto> resultList = service040501.findAll(pageable);

        int price = 0, amount = 0;
        for(Tb417Dto dto : resultList){
            price += dto.getPrice();
            amount += dto.getAmount();
        }
        List<String> footer = GlobalMethod.makeFooter("", "", "", "", "", "", "", String.valueOf(price), String.valueOf(amount), "", "", "", "");
        model.addAttribute("footers", footer);

        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "business/sale/040501";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("입출금관리 input");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));
        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("amountClCodes",service990301.findByFstId("14"));
        model.addAttribute("bankCodes", service010201.findAll());
        model.addAttribute("empCodes", service020101.findAllBySelect());
        if(map.get("vseq").isEmpty()){
            model.addAttribute("dto",new Tb417Dto(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), 0));
            return "business/sale/040501_input";
        }
        model.addAttribute("dto", service040501.findById(Integer.parseInt(map.get("vseq"))));
        return "business/sale/040501_input";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb417Form form, Model model, HttpServletRequest request){
        log.info("입출금관리 insert");
        log.info("form = {}", form);
        if (service040501.insert(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb417Form form, Model model, HttpServletRequest request){
        log.info("입출금관리 update");
        log.info("form = {}", form);
        if(service040501.update(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        log.info("입출금관리 delete");
        if(service040501.delete(id)) model.addAttribute("msg", "정상적으로 삭제 되었습니다.");
        else model.addAttribute("msg", "문제가 발생하였습니다");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/excel")
    public void excel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("입출금관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "입출금일자", "입출금순번", "입출/구분", "입출타입",
                "계좌", "매출액", "입출금액", "거래처명", "사원명", "적요", "참조"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String", "String",
                "Tint", "Tint", "String", "String", "String", "String"
        );

        List<List<String>> excelData = service040501.findAllByExcel();
        String fileName = "입출금관리(040501).xlsx";

        excelMaker.makeExcel("입출금관리 (040501)", titles, excelData, excelType, fileName, response);
    }
}
