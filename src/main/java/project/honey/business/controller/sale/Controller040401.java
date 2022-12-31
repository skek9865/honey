package project.honey.business.controller.sale;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.honey.business.dto.sale.Tb415Dto;
import project.honey.business.dto.sale.Tb415MainDto;
import project.honey.business.dto.sale.Tb415_1Dto;
import project.honey.business.form.sale.Search040401;
import project.honey.business.form.sale.Tb415Form;
import project.honey.business.service.basic.Service040103;
import project.honey.business.service.basic.Service040109;
import project.honey.business.service.basic.Service040110;
import project.honey.business.service.sale.Service040401;
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
@RequestMapping("/040401")
public class Controller040401 {

    private final Service040401 service040401;
    private final Service020101 service020101;
    private final Service040103 service040103;
    private final Service040109 service040109;
    private final Service040110 service040110;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search040401 search,
                          Model model, Pageable pageable){
        log.info("발주서관리 메인");
        log.info("menuId = {}", menuIdDto);
        log.info("search = {}", search);
        if(search.getSYmd1() == null || search.getSYmd2() == null){
            search.setYmd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "발주번호", "거래처명", "담당자",
                "품목", "납기일자", "금액", "상태", "인쇄하기"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        model.addAttribute("empCodes",service020101.findAllBySelect());
        model.addAttribute("statusCodes",service990301.findByFstId("12"));

        Page<Tb415MainDto> resultList = service040401.findAllByDsl(search, pageable);

        int price = 0;
        for(Tb415MainDto dto : resultList) price += dto.getPrice();
        List<String> footer = GlobalMethod.makeFooter("", "", "", "", "", "", "", String.valueOf(price), "", "");
        model.addAttribute("footers", footer);

        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "business/sale/040401";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("발주서관리 input");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));

        List<String> titles = GlobalMethod.makeTitle(
                "삭제", "코드", "품목", "규격", "수량",
                "단가", "공급가액", "부가세", "납기일자", "적요"
        );

        GlobalConst globalConst = new GlobalConst();

        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", globalConst);
        model.addAttribute("titles",titles);

        model.addAttribute("empCodes",service020101.findAllBySelect());
        model.addAttribute("whouseCodes",service040103.findAllBySelect());
        model.addAttribute("saleTypeCodes",service990301.findByFstId("11"));
        model.addAttribute("excgCodes",service040110.findAllBySelect());
        model.addAttribute("projectCodes",service040109.findAllBySelect());
        model.addAttribute("statusCodes",service990301.findByFstId("12"));

        Long orderNo = service040401.findOrderNo();

        List<Tb415_1Dto> subDtos = new ArrayList<>();

        if(map.get("vseq").isEmpty()){
            for(int i = 0; i < globalConst.getSubInputIdx(); i++) subDtos.add(new Tb415_1Dto(0));
            model.addAttribute("dto",new Tb415Dto(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), orderNo.intValue()));
            model.addAttribute("subDtos",subDtos);
            return "business/sale/040401_input";
        }
        Tb415Dto dto = service040401.findById(Integer.parseInt(map.get("vseq")));
        List<Tb415_1Dto> findTb415_1 = service040401.findChildByFk(dto.getSeq());

        int qtyT = 0, amtT = 0, vatT = 0, totT = 0;
        for(Tb415_1Dto subDto : findTb415_1){
            qtyT += subDto.getQty();
            amtT += subDto.getAmt();
            vatT += subDto.getVat();
        }
        totT += (amtT + vatT);
        model.addAttribute("qtyT", qtyT);
        model.addAttribute("amtT", amtT);
        model.addAttribute("vatT", vatT);
        model.addAttribute("totT", totT);

        if(findTb415_1.size() <= 5){
            for(int i = 0; i < globalConst.getSubInputIdx(); i++) {
                if(findTb415_1.size() - 1 < i) subDtos.add(new Tb415_1Dto(0));
                else subDtos.add(findTb415_1.get(i));
            }
        }
        else{
            for(int i = findTb415_1.size() - 1; i < globalConst.getSubInputIdx(); i++) subDtos.add(findTb415_1.get(i));
        }
        model.addAttribute("dto", dto);
        model.addAttribute("subDtos", subDtos);
        return "business/sale/040401_input";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb415Form tb415Form,
                         Model model, HttpServletRequest request){
        log.info("발주서관리 insert");
        log.info("tb415Form = {}", tb415Form);
        log.info("tb415_1Forms = {}", tb415Form.getTb415_1Forms());
        if (service040401.insert(tb415Form, tb415Form.getTb415_1Forms())) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb415Form tb415Form,
                         Model model, HttpServletRequest request){
        log.info("발주서관리 update");
        log.info("tb415Form = {}", tb415Form);
        log.info("tb415_1Forms = {}", tb415Form.getTb415_1Forms());
        if(service040401.update(tb415Form, tb415Form.getTb415_1Forms())) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        log.info("발주서관리 delete");
        if(service040401.delete(id)) model.addAttribute("msg", "정상적으로 삭제 되었습니다.");
        else model.addAttribute("msg", "문제가 발생하였습니다");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/excel")
    public void excel(@ModelAttribute("search") Search040401 search,
                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("발주서관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "발주번호", "거래처명", "담당자",
                "품목", "납기일자", "금액", "상태", "인쇄하기"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String",
                "String", "Tint", "String", "String"
        );

        List<List<String>> excelData = service040401.findAllByExcel(search);
        String fileName = "발주서관리(040401).xlsx";

        excelMaker.makeExcel("발주서관리 (040401)", titles, excelData, excelType, fileName, response);
    }
}
