package project.honey.system.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Service990301ImplTest {

    @Autowired
    private Service990301 service990301;

    @Test
    void findByFstId() {
        service990301.findByFstId("99").forEach(System.out::println);
    }

    @Test
    void findFstIdAll() {
        service990301.findFstIdAll().forEach(System.out::println);
    }
}