package project.honey.system.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.honey.system.entity.Tb904;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Service990201ImplTest {

    @Autowired
    private Service990201 service990201;

    @Test
    void findThdMenuAll() {
        Map<Integer, String> thdMenuAll = service990201.findThdMenuAll();
        for (Integer seq : thdMenuAll.keySet()) {
            System.out.println("map = " + seq + " , " + thdMenuAll.get(seq));
        }
    }
}