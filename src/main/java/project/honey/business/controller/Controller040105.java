package project.honey.business.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import project.honey.business.dto.search.Search405;
import project.honey.business.dto.Tb405Dto;
import project.honey.business.dto.search.SearchPopUp405;
import project.honey.business.form.Tb405Form;
import project.honey.business.service.Service040104;
import project.honey.business.service.Service040105;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.PageMaker;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;
import project.honey.produce.service.Service050101;
import project.honey.system.service.Service990301;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/040105")
public class Controller040105 {

    private final Service040104 service040104;
    private final Service040105 service040105;
    private final Service050101 service050101;
    private final Service990301 service990301;
    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping
    public String findAll(@ModelAttribute("menuId") MenuIdDto menuIdDto, @ModelAttribute("search")
            Search405 search, Model model, Pageable pageable){
        log.info("품목관리 메인");
        log.info("menuId = {}", menuIdDto);
        log.info("search = {}", search);
        List<String> titles = GlobalMethod.makeTitle(
                "순번", "관리", "품목코드", "품목명", "규격",
                "단위", "품목구분", "세트여부", "재고수량관리", "생산공정", "품목그룹1",
                "품목그룹2", "재고수량", "안전재고", "입고단가", "입고VAT", "출고단가",
                "출고VAT", "상품이미지저장명", "상품이미지출력명"
        );

        model.addAttribute("menus", menuMaker.getMenuId(1,"","",""));
        model.addAttribute("menuNm",menuMaker.getMenuNm(menuIdDto));
        model.addAttribute("titles",titles);
        model.addAttribute("global", new GlobalConst());

        Page<Tb405Dto> resultList = service040105.findAll(search, pageable);
        model.addAttribute("dtos", resultList);
        model.addAttribute("pageMaker", new PageMaker(pageable, resultList.getTotalElements()));

        // 푸터 리스트
        List<Tb405Dto> content = resultList.getContent();
        int stockQty = 0;
        int aQty = 0;
        for (Tb405Dto dto : content) {
            stockQty += dto.getStockQty() != null ? dto.getStockQty() : 0;
            aQty += dto.getAQty() != null ? dto.getAQty(): 0;
        }
        List<String> footers = GlobalMethod.makeFooter("", "", "", "", "", "", "", "", "", "", "", "",
                String.valueOf(stockQty),
                String.valueOf(aQty),
                "", "", "", "", "", "");

        model.addAttribute("footers", footers);

        //셀렉트박스 데이터
        model.addAttribute("itemGb1s", service040104.findAllBySelect());

        return "business/040105";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Tb405Form form, Model model, HttpServletRequest request) throws IOException {
        log.info("품목관리 insert");
        log.info("form = {}", form);
        if (service040105.insert(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Tb405Form form, Model model, HttpServletRequest request){
        log.info("품목관리 update");
        log.info("form = {}", form);
        if(service040105.update(form)) model.addAttribute("msg","정상적으로 저장 되었습니다.");
        else model.addAttribute("msg","문제가 발생 하였습니다.");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        log.info("품목관리 delete");
        if(service040105.delete(id)) model.addAttribute("msg", "정상적으로 삭제 되었습니다.");
        else model.addAttribute("msg", "문제가 발생하였습니다");
        model.addAttribute("url", request.getHeader("referer"));
        return "redirect";
    }

    @GetMapping("/input")
    public String findById(@RequestParam Map<String, String> map, Model model){
        log.info("품목관리 input");
        log.info("fstId = {}, scdId = {}, thdId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"));
        model.addAttribute("fstId", map.get("fstId"));
        model.addAttribute("scdId", map.get("scdId"));
        model.addAttribute("thdId", map.get("thdId"));
        model.addAttribute("action", map.get("action"));
        model.addAttribute("global", new GlobalConst());

        // 셀렉트박스 데이터
        model.addAttribute("classSeqCodes", service990301.findByFstId("07"));
        model.addAttribute("productCodes", service050101.findAllBySelect());
        model.addAttribute("itemGbCodes", service040104.findAllBySelect());

        if(map.get("vseq").isEmpty()){
            model.addAttribute("dto",new Tb405Dto());
            return "business/040105_input";
        }

        Tb405Dto dto = service040105.findById(Integer.parseInt(map.get("vseq")));
        try {
            String imgName = "/images/patent/" + makeFileName(dto.getImgNmSave(), dto.getSeq());
            model.addAttribute("imgName", imgName);
        } catch (NullPointerException e) {
            model.addAttribute("imgName", null);
        }

        model.addAttribute("dto", dto);
        return "business/040105_input";
    }

    @GetMapping("/popup")
    public String popup(@ModelAttribute("search") SearchPopUp405 search, Model model) {
        model.addAttribute("global", new GlobalConst());

        List<String> titles = GlobalMethod.makeTitle(
                "코드", "품목명", "규격", "생산공정", "재고", "선택"
        );

        if(!search.isProduct()) titles.remove("생산공정");
        if(!search.isStock()) titles.remove("재고");

        model.addAttribute("titles", titles);

        if (StringUtils.hasText(search.getGoodsNm())) {
            model.addAttribute("dtos", service040105.findAllByPopUp(search));
        } else {
            model.addAttribute("dtos", null);
        }
        return "goods_popup";
    }


    @GetMapping("/excel")
    public void excel(Search405 search, HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("품목관리 excel");
        log.info("url = {}", request.getHeader("referer"));

        List<String> titles = GlobalMethod.makeTitle(
                "순번", "품목코드", "품목명", "규격",
                "단위", "품목구분", "세트여부", "재고수량관리", "생산공정", "품목그룹1",
                "품목그룹2", "재고수량", "안전재고", "입고단가", "입고VAT", "출고단가",
                "출고VAT", "상품이미지저장명", "상품이미지출력명"
        );
        List<String> excelType = GlobalMethod.makeExcelType(
                "String", "String", "String", "String", "String",
                "String", "String", "String", "String", "String", "int", "int", "int",
                "String", "int", "String", "String", "String"
        );

        List<List<String>> excelData = service040105.findAllByExcel(search);
        String fileName = "품목관리(040105).xls";

        excelMaker.makeExcel("품목관리 (040105)", titles, excelData, excelType, fileName, response);
    }

    private String makeFileName(String originalFilename, Integer id){
        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1);
        return String.valueOf(id) + "." + ext;
    }
}
