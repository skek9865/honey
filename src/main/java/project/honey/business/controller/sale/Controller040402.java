package project.honey.business.controller.sale;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.honey.business.dto.sale.*;
import project.honey.business.dto.search.SearchPopUp410;
import project.honey.business.form.sale.Search040402;
import project.honey.business.form.sale.Tb416Form;
import project.honey.business.service.basic.Service040101;
import project.honey.business.service.basic.Service040103;
import project.honey.business.service.basic.Service040109;
import project.honey.business.service.basic.Service040110;
import project.honey.business.service.sale.Service040401;
import project.honey.business.service.sale.Service040402;
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
@RequestMapping("/040402")
public class Controller040402 {

    private final Service040402 service040402;
    private final Service020101 service020101;
    private final Service040101 service040101;
    private final Service040103 service040103;
    private final Service040109 service040109;
    private final Service040110 service040110;
    private final Service040401 service040401;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("")
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto,
                          @ModelAttribute("search") Search040402 search,
                          Model model, Pageable pageable){
        log.info("???????????? ??????");
        log.info("menuId = {}", menuIdDto);
        log.info("search = {}", search);
        if(search.getSYmd1() == null || search.getSYmd2() == null){
            search.setYmd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        List<String> titles = GlobalMethod.makeTitle(
                "??????", "??????", "????????????", "????????????", "??????", "??????",
                "??????", "????????????", "????????????", "??????", "????????????"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        model.addAttribute("whouseCodes",service040103.findAllBySelect());
        model.addAttribute("saleTypeCodes",service990301.findByFstId("11"));
        model.addAttribute("projectCodes",service040109.findAllBySelect());
        model.addAttribute("custGrCodes",service040101.findAllBySelect(1));
        model.addAttribute("statusCodes",service990301.findByFstId("12"));
        model.addAttribute("empCodes",service020101.findAllBySelect());

        Page<Tb416MainDto> resultList = service040402.findAllByDsl(search, pageable);

        int qty = 0, price = 0;
        for(Tb416MainDto dto : resultList) {
            qty += dto.getQty();
            price += dto.getPrice();
        }
        List<String> footer = GlobalMethod.makeFooter("", "", "", "", "", String.valueOf(price), String.valueOf(qty), "", "", "", "");
        model.addAttribute("footers", footer);

        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        return "business/sale/040402";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("???????????? input");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));

        List<String> titles = GlobalMethod.makeTitle(
                "??????", "??????", "??????", "??????", "??????",
                "??????", "????????????", "?????????", "??????"
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
        model.addAttribute("projectCodes",service040109.findAllBySelect());
        model.addAttribute("statusCodes",service990301.findByFstId("12"));

        Long buyNo = service040402.findBuyNo();

        List<Tb416_1Dto> subDtos = new ArrayList<>();

        if(map.get("vseq").isEmpty()){
            for(int i = 0; i < globalConst.getSubInputIdx(); i++) subDtos.add(new Tb416_1Dto(0));
            model.addAttribute("dto",new Tb416Dto(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), buyNo.intValue()));
            model.addAttribute("subDtos",subDtos);
            return "business/sale/040402_input";
        }
        Tb416Dto dto = service040402.findById(Integer.parseInt(map.get("vseq")));
        List<Tb416_1Dto> findTb416_1 = service040402.findChildByFk(dto.getSeq());

        int qtyT = 0, amtT = 0, vatT = 0, totT = 0;
        for(Tb416_1Dto subDto : findTb416_1){
            qtyT += subDto.getQty();
            amtT += subDto.getAmt();
            vatT += subDto.getVat();
        }
        totT += (amtT + vatT);
        model.addAttribute("qtyT", qtyT);
        model.addAttribute("amtT", amtT);
        model.addAttribute("vatT", vatT);
        model.addAttribute("totT", totT);

        if(findTb416_1.size() <= 5){
            for(int i = 0; i < globalConst.getSubInputIdx(); i++) {
                if(findTb416_1.size() - 1 < i) subDtos.add(new Tb416_1Dto(0));
                else subDtos.add(findTb416_1.get(i));
            }
        }
        else{
            for(int i = findTb416_1.size() - 1; i < globalConst.getSubInputIdx(); i++) subDtos.add(findTb416_1.get(i));
        }
        model.addAttribute("dto", dto);
        model.addAttribute("subDtos", subDtos);
        return "business/sale/040402_input";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb416Form tb416Form,
                         Model model, HttpServletRequest request){
        log.info("???????????? insert");
        log.info("tb416Form = {}", tb416Form);
        log.info("tb416_1Forms = {}", tb416Form.getTb416_1Forms());
        if (service040402.insert(tb416Form, tb416Form.getTb416_1Forms())) model.addAttribute("msg","??????????????? ?????? ???????????????.");
        else model.addAttribute("msg","????????? ?????? ???????????????.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb416Form tb416Form,
                         Model model, HttpServletRequest request){
        log.info("???????????? update");
        log.info("tb416Form = {}", tb416Form);
        log.info("tb416_1Forms = {}", tb416Form.getTb416_1Forms());
        if(service040402.update(tb416Form, tb416Form.getTb416_1Forms())) model.addAttribute("msg","??????????????? ?????? ???????????????.");
        else model.addAttribute("msg","????????? ?????? ???????????????.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        log.info("???????????? delete");
        if(service040402.delete(id)) model.addAttribute("msg", "??????????????? ?????? ???????????????.");
        else model.addAttribute("msg", "????????? ?????????????????????");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/excel")
    public void excel(@ModelAttribute("search") Search040402 search,
                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("???????????? excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "??????", "????????????", "????????????", "??????", "??????",
                "??????", "????????????", "????????????", "??????", "????????????"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "Tint",
                "Tint", "String", "String", "String", "String"
        );

        List<List<String>> excelData = service040402.findAllByExcel(search);
        String fileName = "????????????(040402).xlsx";

        excelMaker.makeExcel("???????????? (040402)", titles, excelData, excelType, fileName, response);
    }

    @GetMapping("/orderPopUp")
    public String orderPopUp(@ModelAttribute("search") SearchPopUp410 search, Model model){
        log.info("??????????????? PopUp");
        model.addAttribute("global", new GlobalConst());

        if(search.getSYmd1() == null || search.getSYmd2() == null){
            search.setYmd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        List<String> titles = GlobalMethod.makeTitle("??????", "????????????", "????????????", "?????????", "??????", "??????", "??????");
        model.addAttribute("titles", titles);

        List<PopUp040401Dto> result = service040401.findAllByPopUp(search);
        model.addAttribute("dtos", result);
        return "business/sale/040402_1";
    }

    @GetMapping("/popInput")
    public String findByPopUp(@RequestParam Map<String, String> map, Model model){
        log.info("???????????? popInput");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));

        List<String> titles = GlobalMethod.makeTitle(
                "??????", "??????", "??????", "??????", "??????",
                "??????", "????????????", "?????????", "??????"
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
        model.addAttribute("projectCodes",service040109.findAllBySelect());
        model.addAttribute("statusCodes",service990301.findByFstId("12"));

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        model.addAttribute("date", date);

        Long buyNo = service040402.findBuyNo();
        model.addAttribute("buyNo", buyNo.intValue());

        List<Tb415_1Dto> subPopDtos = new ArrayList<>();
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
                if(findTb415_1.size() - 1 < i) subPopDtos.add(new Tb415_1Dto(0));
                else subPopDtos.add(findTb415_1.get(i));
            }
        }
        else{
            for(int i = findTb415_1.size() - 1; i < globalConst.getSubInputIdx(); i++) subPopDtos.add(findTb415_1.get(i));
        }
        model.addAttribute("dto", dto);
        model.addAttribute("subDtos", subPopDtos);
        return "business/sale/040402_popInput";
    }
}
