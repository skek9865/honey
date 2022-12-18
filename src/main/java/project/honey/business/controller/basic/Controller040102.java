package project.honey.business.controller.basic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import project.honey.business.dto.basic.Tb402Dto;
import project.honey.business.dto.search.SearchPopUp402;
import project.honey.business.form.basic.Tb402Form;
import project.honey.business.service.basic.Service040101;
import project.honey.business.service.basic.Service040102;
import project.honey.business.service.basic.Service040107;
import project.honey.business.service.basic.Service040110;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.personDepart.service.Service020101;
import project.honey.system.service.Service990301;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/040102")
public class Controller040102 {

    private final Service040102 service040102;
    private final Service020101 service020101;
    private final Service040101 service040101;
    private final Service040107 service040107;
    private final Service040110 service040110;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@RequestParam Map<String, String> map,
                          @ModelAttribute("menuId") MenuIdDto menuIdDto,
                          Pageable pageable, Model model){

        log.info("거래처관리 메인");
        log.info("custNm = {}, ceoNm = {}, empCd = {}, class1 = {}" ,map.get("sCustNm"), map.get("sCeoNm"), map.get("sEmpCd"), map.get("sClass1"));
        log.info("menuId = {}", menuIdDto);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "거래처코드", "상호(이름)", "대표자명",
                "전화번호", "모바일", "거래처Fax", "담당자", "영업사원",
                "거래처그룹1", "거래처그룹2", "영업단가그룹", "구매단가그룹"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        model.addAttribute("sCustNm",  map.get("sCustNm"));
        model.addAttribute("sCeoNm",   map.get("sCeoNm"));
        model.addAttribute("sEmpCd", map.get("sEmpCd"));
        model.addAttribute("sClass1", map.get("sClass1"));

        Page<Tb402Dto> resultList = service040102.findAllByDsl(map.get("sCustNm"), map.get("sCeoNm"), map.get("sEmpCd"), map.get("sClass1"),pageable);
        model.addAttribute("dtos", resultList);
        model.addAttribute("empCodes",service020101.findAllBySelect());
        model.addAttribute("class1Codes",service040101.findAllBySelect(1));
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "business/040102";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("거래처관리 input");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));
        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", new GlobalConst());
        model.addAttribute("custGbCodes",service990301.findByFstId("08"));
        model.addAttribute("taxGbCodes",service990301.findByFstId("09"));
        model.addAttribute("typeCodes",service990301.findByFstId("11"));
        model.addAttribute("empCodes",service020101.findAllBySelect());
        model.addAttribute("specialCodes", service040107.findAllBySelect());
        model.addAttribute("excgCodes", service040110.findAllBySelect());
        model.addAttribute("class1Codes",service040101.findAllBySelect(1));
        model.addAttribute("class2Codes",service040101.findAllBySelect(2));
        if(map.get("vseq").isEmpty()){
            model.addAttribute("dto",new Tb402Dto());
            return "business/040102_input";
        }
        model.addAttribute("dto", service040102.findById(Integer.parseInt(map.get("vseq"))));
        return "business/040102_input";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb402Form form, Model model, HttpServletRequest request){
        log.info("거래처관리 insert");
        log.info("form = {}", form);
        if (service040102.insert(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb402Form form, Model model, HttpServletRequest request){
        log.info("거래처관리 update");
        log.info("form = {}", form);
        if(service040102.update(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        log.info("거래처관리 delete");
        if(service040102.delete(id)) model.addAttribute("msg", "정상적으로 삭제 되었습니다.");
        else model.addAttribute("msg", "문제가 발생하였습니다");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/excel")
    public void excel(@RequestParam Map<String, String> map,
                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("거래처관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "거래처코드", "상호(이름)", "대표자명",
                "전화번호", "모바일", "거래처Fax", "담당자", "영업사원",
                "거래처그룹1", "거래처그룹2", "영업단가그룹", "구매단가그룹"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String", "String",
                "String", "String", "String", "String", "String", "String", "String"
                );

        List<List<String>> excelData = service040102.findAllByExcel(map.get("sCustNm"), map.get("sCeoNm"), map.get("sEmpCd"), map.get("sClass1"));

        String fileName = "거래처관리(040102).xlsx";

        excelMaker.makeExcel("거래처관리 (040102)", titles, excelData, excelType, fileName, response);
    }

    @GetMapping("/popup")
    public String popup(@ModelAttribute("search") SearchPopUp402 search, Model model) {
        model.addAttribute("global", new GlobalConst());

        List<String> titles = GlobalMethod.makeTitle(
                "거래처코드", "상호(이름)", "대표자명", "담당자", "선택"
        );

        model.addAttribute("titles", titles);

        if (StringUtils.hasText(search.getCustNm())) {
            model.addAttribute("dtos", service040102.findAllByPopup(search.getCustNm()));
        } else {
            model.addAttribute("dtos", null);
        }
        return "cust_popup";
    }
}
