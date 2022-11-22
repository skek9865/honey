package project.honey.comm.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
public class MenuMakerTest {

    @Autowired
    private MenuMaker menuMaker;

    @Test
    public void menu(){
        List<MenuIdDto> result1 = menuMaker.getMenuId(2, "01", "", "aaa");

        MenuIdDto fst = result1.stream().filter(dto -> dto.getScdId().equals("00")).findAny().orElseThrow(RuntimeException::new);
        List<MenuIdDto> menus2 = result1.stream().filter(dto -> !dto.getScdId().equals("00")).filter(dto -> dto.getThdId().equals("00")).collect(Collectors.toList());
        List<MenuIdDto> menus3 = result1.stream().filter(dto -> !dto.getScdId().equals("00")).filter(dto -> !dto.getThdId().equals("00")).collect(Collectors.toList());

        System.out.println("fstNm = " + fst.getMenuNm());

        System.out.println("scdMenu");
        menus2.stream().forEach(System.out::println);
        System.out.println("thdMenu");
        menus3.stream().forEach(System.out::println);

        result1.forEach(dto -> {
            System.out.println(dto);
        });
    }

    @Test
    public void menuNm(){
        MenuNm menuNm = menuMaker.getMenuNm(new MenuIdDto("02", "01", "01",""));
        System.out.println(menuNm);
    }
}
