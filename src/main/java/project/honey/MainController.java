package project.honey;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.honey.comm.ExcelMaker;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final MenuMaker menuMaker;
    private final ExcelMaker excelMaker;

    @GetMapping("/")
    public String main(Model model){
        log.info("mainController");
        model.addAttribute("menus", menuMaker.getMenuId(1,"00","00",""));
        model.addAttribute("menuId", new MenuIdDto("01","00","00",""));
        model.addAttribute("global", new GlobalConst());
        return "main";
    }
    @GetMapping("/menu")
    public String menu(@RequestParam Map<String,String> map, Model model){
        log.info("menuController");
        log.info("fstId = {}, scdId = {}, thdId = {}, userId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"), map.get("userId"));

        List<MenuIdDto> result = menuMaker.getMenuId(2, map.get("fstId"), "00", map.get("userId"));
        MenuIdDto fst = result.stream().filter(dto -> dto.getScdId().equals("00")).findAny().orElseThrow(RuntimeException::new);

        List<MenuIdDto> menus2 = result.stream().filter(dto -> !dto.getScdId().equals("00")).filter(dto -> dto.getThdId().equals("00")).collect(Collectors.toList());
        List<MenuIdDto> menus3 = result.stream().filter(dto -> !dto.getScdId().equals("00")).filter(dto -> !dto.getThdId().equals("00")).collect(Collectors.toList());

        model.addAttribute("menuNm", fst.getMenuNm());
        model.addAttribute("scdId",map.get("scdId"));
        model.addAttribute("menus2", menus2);
        model.addAttribute("menus3", menus3);
        return "menu";
    }

}
