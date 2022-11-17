package project.honey;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import project.honey.comm.GlobalConst;
import project.honey.comm.menu.MenuAjax;
import project.honey.comm.menu.MenuIdDto;
import project.honey.comm.menu.MenuMaker;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final MenuMaker menuMaker;

    @GetMapping("/")
    public String main(Model model){
        log.info("mainController");
        model.addAttribute("menus", menuMaker.getMenuId(1,"00","00",""));
        model.addAttribute("global", new GlobalConst());
        return "main";
    }
    @GetMapping("/menu")
    public String menu(@RequestParam Map<String,String> map, Model model){
        log.info("menuController");
        log.info("fstId = {}, scdId = {}, thdId = {}, userId = {}", map.get("fstId"), map.get("scdId"), map.get("thdId"), map.get("userId"));

        List<MenuIdDto> result2 = menuMaker.getMenuId(2, map.get("fstId"), "00", map.get("userId"));
        List<MenuIdDto> result3 = menuMaker.getMenuId(3, map.get("fstId"), map.get("scdId"), map.get("userId"));

        Optional<MenuIdDto> any = result2.stream().filter(dto -> dto.getScdId().equals("00")).findAny();

        List<MenuIdDto> menus2 = result2.stream().filter(dto -> !dto.getScdId().equals("00")).collect(Collectors.toList());
        List<MenuIdDto> menus3 = result3.stream().filter(dto -> !dto.getScdId().equals("00")).filter(dto -> !dto.getThdId().equals("00")).collect(Collectors.toList());

        MenuIdDto fst = any.orElseThrow(() -> new RuntimeException());
        model.addAttribute("fst", fst);
        model.addAttribute("scdId",map.get("userId"));
        model.addAttribute("menus2", menus2);
        model.addAttribute("menus3", menus3);
        return "menu";
    }
}
