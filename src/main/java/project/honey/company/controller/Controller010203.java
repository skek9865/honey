package project.honey.company.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.company.dto.Tb104Dto;
import project.honey.company.service.Service010201;
import project.honey.company.service.Service010202;
import project.honey.company.service.Service010203;
import project.honey.personDepart.service.Service020101;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/010203")
public class Controller010203 {

    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;
    private final Service010203 service010203;
    private final Service020101 service020101;
    private final Service010201 service010201;
    private final Service010202 service010202;

    @GetMapping()
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, Model model, Pageable pageable){

        Page<Tb104Dto> resultList = service010203.findAll(pageable);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "카드명", "카드번호", "유효기간", "cvc번호",
                "사용여부", "사용자", "결제계좌", "한도", "발급일자", "인증서", "비고"
        );

        //footer (총 한도)
        List<String> footer = GlobalMethod.makeFooter(
                "", "", "", "", "", "", "", "", "", service010203.getTotalLimitamt().toString(), "", "", ""
        );

        model.addAttribute("global", new GlobalConst());
        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));

        model.addAttribute("titles",titles);
        model.addAttribute("dtos",resultList);
        model.addAttribute("footer",footer);

        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "company/010203";
    }
    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("fstId = {}", map.get("fstId"));
        log.info("scdId = {}", map.get("scdId"));
        log.info("thdId = {}", map.get("thdId"));
        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", new GlobalConst());
        //사원 list
        model.addAttribute("empList", service020101.findAllBySelect());
        //계좌 list
        model.addAttribute("bankList", service010201.findAll());
        //인증서 list
        model.addAttribute("cerList", service010202.findAll());

        if(map.get("vseq").isEmpty()){
            model.addAttribute("dto",new Tb104Dto());
            return "company/010203_input";
        }
        model.addAttribute("dto", service010203.findById(Integer.parseInt(map.get("vseq"))));
        return "company/010203_input";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb104Dto dto, Model model, HttpServletRequest request){
        log.info("dto : {}", dto);

        model.addAttribute("msg", service010203.insert(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));

        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb104Dto dto, Model model, HttpServletRequest request){
        log.info("dto : {}", dto);

        model.addAttribute("msg", service010203.update(dto) != null ? "정상적으로 수정 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));

        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model ,HttpServletRequest request) {

        log.info("id : " + id);

        model.addAttribute("msg", service010203.delete(id) != null ? "정상적으로 삭제 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/excel")
    public void excel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "카드명", "카드번호", "유효기간", "cvc번호",
                "사용여부", "사용자", "결제계좌", "한도", "발급일자",
                "인증서", "비고"
        );

        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String", "String",
                "String", "String", "Tint", "String", "String",
                "String"
        );

        List<List<String>> excelData = service010203.findAllByExcel();
        String fileName = "카드관리(010203)";
        excelMaker.makeExcel("카드관리(010203)", titles, excelData, excelType,fileName, response);
    }

}
