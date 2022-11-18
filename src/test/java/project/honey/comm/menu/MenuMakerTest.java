package project.honey.comm.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MenuMakerTest {

    @Autowired
    private MenuMaker menuMaker;

    @Test
    public void menu(){
        List<MenuIdDto> result1 = menuMaker.getMenuId(3, "02", "01", "aaa");
        result1.forEach(dto -> {
            System.out.println(dto);
        });
    }

    @Test
    public void menuNm(){
        MenuNm menuNm = menuMaker.getMenuNm(new QueryParam("02", "01", "01"));
        System.out.println(menuNm);
    }
}
