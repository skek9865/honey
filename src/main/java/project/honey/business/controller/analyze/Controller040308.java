package project.honey.business.controller.analyze;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.honey.business.dto.analaze.Dto040308;
import project.honey.business.service.analyze.Service040308;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;

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
@RequestMapping("/040308")
public class Controller040308 {
    
    private final Service040308 service040308;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @RequestParam Map<String, String> map,
                          Model model, Pageable pageable){
        log.info("수금현황 메인");
        log.info("menuId = {}", menuIdDto);
        log.info("sYmd1 = {} , sYmd2 = {}",map.get("sYmd1"), map.get("sYmd2"));
        String sYmd1 = map.get("sYmd1");
        String sYmd2 = map.get("sYmd2");
        if(map.get("sYmd1") == null || map.get("sYmd2") == null){
            sYmd1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            sYmd2 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        model.addAttribute("sYmd1", sYmd1);
        model.addAttribute("sYmd2", sYmd2);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "입금번호", "거래처", "금액", "적요"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Dto040308> resultList = service040308.findAllByDsl(sYmd1, sYmd2, pageable);

        int price = 0;
        for(Dto040308 dto : resultList) price += dto.getPrice(); 
        List<String> footer = GlobalMethod.makeFooter("", "", "", String.valueOf(price), "");
        model.addAttribute("footers", footer);

        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "business/analyze/040308";
    }

    @GetMapping("/excel")
    public void excel(@RequestParam Map<String, String> map,
                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("수금현황 excel");
        log.info("url = {}", request.getHeader("referer"));

        String sYmd1 = map.get("sYmd1");
        String sYmd2 = map.get("sYmd2");

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "입금번호", "거래처", "금액", "적요"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "Tint", "String"
        );

        List<List<String>> excelData = service040308.findAllByExcel(sYmd1, sYmd2);
        String fileName = "수금현황(040308).xlsx";

        excelMaker.makeExcel("수금현황 (040308)", titles, excelData, excelType, fileName, response);
    }
}
