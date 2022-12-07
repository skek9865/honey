package project.honey.business.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import project.honey.business.dto.Tb402Dto;

@SpringBootTest
public class Service040102Test {

    @Autowired
    private Service040102 service040102;

    @Test
    public void findAllByDsl(){
        Page<Tb402Dto> result = service040102.findAllByDsl(null, null, null, null, PageRequest.of(0, 50));
        result.forEach(System.out::println);
    }
}
