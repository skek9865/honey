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
import project.honey.company.dto.Tb105Dto;
import project.honey.company.service.Service010201;
import project.honey.company.service.Service010204;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/010204")
public class Controller010204 {

    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;
    private final Service010204 service010204;
    private final Service010201 service010201;

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

        //계좌 list
        model.addAttribute("bankList", service010201.findAll());


        if(map.get("vseq").isEmpty()){
            model.addAttribute("dto",new Tb105Dto());
            return "company/010204_input";
        }
        model.addAttribute("dto", service010204.findById(Integer.parseInt(map.get("vseq"))));
        Tb105Dto dto = service010204.findById(Integer.parseInt(map.get("vseq")));
        log.info("dto == {}",dto);
        return "company/010204_input";
    }
    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb105Dto dto, Model model, HttpServletRequest request){
        log.info("dto : {}", dto);

        model.addAttribute("msg", service010204.insert(dto) != null ? "정상적으로 저장 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));

        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb105Dto dto, Model model, HttpServletRequest request){
        log.info("dto : {}", dto);

        model.addAttribute("msg", service010204.update(dto) != null ? "정상적으로 수정 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));

        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model ,HttpServletRequest request) {

        log.info("id : " + id);

        model.addAttribute("msg", service010204.delete(id) != null ? "정상적으로 삭제 되었습니다." : "문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/excel")
    public void excel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "대출명", "계좌번호", "신규일", "만기일",
                "약정한도", "납입금", "대출잔액", "이자일", "금리",
                "비고"
        );

        List<String> excelType = GlobalMethod.makeExcelType(
                  "String", "String", "String", "String",
                "Tint", "Tint", "Tint", "String", "String",
                "String"
        );

        List<List<String>> excelData = service010204.findAllByExcel();
        String fileName = "대출관리(010204)";
        excelMaker.makeExcel("대출관리(010204)", titles, excelData, excelType,fileName, response);
    }
}
