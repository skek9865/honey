package project.honey.business.controller.manage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.honey.business.dto.manage.*;
import project.honey.business.form.manage.Search040201;
import project.honey.business.form.manage.Tb410Form;
import project.honey.business.service.basic.Service040103;
import project.honey.business.service.basic.Service040110;
import project.honey.business.service.manage.Service040201;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/040201")
public class Controller040201 {

    private final Service040201 service040201;
    private final Service020101 service020101;
    private final Service040103 service040103;
    private final Service040110 service040110;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search040201 search,
                          Model model, Pageable pageable){
        log.info("견적서관리 메인");
        log.info("menuId = {}", menuIdDto);
        log.info("search = {}", search);
        if(search.getSYmd1() == null || search.getSYmd2() == null){
            search.setYmd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "견적번호", "거래처명", "담당자",
                "품목", "유효기간", "금액", "상태", "인쇄하기", "참조"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        model.addAttribute("empCodes",service020101.findAllBySelect());
        model.addAttribute("statusCodes",service990301.findByFstId("12"));

        Page<Tb410MainDto> resultList = service040201.findAllByDsl(search, pageable);

        int price = 0;
        for(Tb410MainDto dto : resultList) price += dto.getPrice();
        List<String> footer = GlobalMethod.makeFooter("", "", "", "", "", "", "", String.valueOf(price), "", "", "");
        model.addAttribute("footers", footer);

        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "business/040201";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("견적서관리 input");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));

        List<String> titles = GlobalMethod.makeTitle(
                "삭제", "코드", "품목", "규격", "수량",
                "단가", "공급가액", "부가세", "적요"
        );

        GlobalConst globalConst = new GlobalConst();

        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", globalConst);
        model.addAttribute("titles",titles);

        model.addAttribute("empCodes",service020101.findAllBySelect());
        model.addAttribute("saleTypeCodes",service990301.findByFstId("11"));
        model.addAttribute("whouseCodes",service040103.findAllBySelect());
        model.addAttribute("excgCodes",service040110.findAllBySelect());
        model.addAttribute("statusCodes",service990301.findByFstId("12"));

        Long estNo = service040201.findEstNo();

        List<Tb410_1Dto> subDtos = new ArrayList<>();

        if(map.get("vseq").isEmpty()){
            for(int i = 0; i < globalConst.getSubInputIdx(); i++) subDtos.add(new Tb410_1Dto(0));
            model.addAttribute("dto",new Tb410Dto(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), estNo.intValue()));
            model.addAttribute("subDtos",subDtos);
            return "business/040201_input";
        }
        Tb410Dto dto = service040201.findById(Integer.parseInt(map.get("vseq")));
        List<Tb410_1Dto> findTb410_1 = service040201.findChildByFk(dto.getSeqP());

        int qtyT = 0, amtT = 0, vatT = 0, totT = 0;
        for(Tb410_1Dto subDto : findTb410_1){
            qtyT += subDto.getQty();
            amtT += subDto.getAmt();
            vatT += subDto.getVat();
        }
        totT += (amtT + vatT);
        model.addAttribute("qtyT", qtyT);
        model.addAttribute("amtT", amtT);
        model.addAttribute("vatT", vatT);
        model.addAttribute("totT", totT);

        if(findTb410_1.size() <= 5){
            for(int i = 0; i < globalConst.getSubInputIdx(); i++) {
                if(findTb410_1.size() - 1 < i) subDtos.add(new Tb410_1Dto(0));
                else subDtos.add(findTb410_1.get(i));
            }
        }
        else{
            for(int i = findTb410_1.size() - 1; i < globalConst.getSubInputIdx(); i++) subDtos.add(findTb410_1.get(i));
        }
        model.addAttribute("dto", dto);
        model.addAttribute("subDtos", subDtos);
        return "business/040201_input";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb410Form tb410Form,
                         Model model, HttpServletRequest request){
        log.info("견적서관리 insert");
        log.info("tb410Form = {}", tb410Form);
        log.info("tb410_1Forms = {}", tb410Form.getTb410_1Forms());
        if (service040201.insert(tb410Form, tb410Form.getTb410_1Forms())) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb410Form tb410Form,
                         Model model, HttpServletRequest request){
        log.info("견적서관리 update");
        log.info("tb410Form = {}", tb410Form);
        log.info("tb410_1Forms = {}", tb410Form.getTb410_1Forms());
        if(service040201.update(tb410Form, tb410Form.getTb410_1Forms())) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        log.info("견적서관리 delete");
        if(service040201.delete(id)) model.addAttribute("msg", "정상적으로 삭제 되었습니다.");
        else model.addAttribute("msg", "문제가 발생하였습니다");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/excel")
    public void excel(@ModelAttribute("search") Search040201 search,
                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("견적서관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "견적번호", "거래처명", "담당자",
                "품목", "유효기간", "금액", "상태", "인쇄하기", "참조"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String",
                "String", "Tint", "String", "String", "String"
        );

        List<List<String>> excelData = service040201.findAllByExcel(search);
        String fileName = "견적서관리(040201).xlsx";

        excelMaker.makeExcel("견적서관리 (040201)", titles, excelData, excelType, fileName, response);
    }

    @GetMapping("/popup/{seq}")
    public String popUp(@PathVariable("seq") Integer seq, Model model){
        log.info("견적서관리 print");

        PrintData040201 printData = service040201.findPrintData(seq);
        int totalAmt = 0, total = 0;
        List<PrintData040201_1> tb410_1Dtos = printData.getTb410_1Dtos();
        for(PrintData040201_1 pd : tb410_1Dtos){
            if(pd.getAmt() != null && pd.getVat() != null){
                totalAmt += pd.getAmt();
                total += (pd.getAmt() + pd.getVat());
            }
        }
        model.addAttribute("totalAmt", totalAmt);
        model.addAttribute("total", total);

        model.addAttribute("global", new GlobalConst());
        model.addAttribute("printData", printData);
        model.addAttribute("subPrintData", printData.getTb410_1Dtos());

        return "business/040201_prt";
    }
}
