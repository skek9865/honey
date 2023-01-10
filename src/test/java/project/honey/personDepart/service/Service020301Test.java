package project.honey.personDepart.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.honey.personDepart.dto.PrintData020301;

@SpringBootTest
public class Service020301Test {

    @Autowired
    private Service020301 service020301;

    @Test
    public void getData(){
        PrintData020301 data = service020301.getData("12345");
        System.out.println(data);
    }
}
