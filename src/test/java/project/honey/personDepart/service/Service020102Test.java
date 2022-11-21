package project.honey.personDepart.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Service020102Test {

    @Autowired
    private Service020102 service020102;

    @Test
    public void findAllDept(){
        service020102.findAllDept().forEach(System.out::println);
    }
}
