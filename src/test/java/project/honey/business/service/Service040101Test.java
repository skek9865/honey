package project.honey.business.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.honey.business.service.basic.Service040101;
import project.honey.system.dto.CodeDto;

import java.util.List;

@SpringBootTest
public class Service040101Test {

    @Autowired
    private Service040101 service040101;

    @Test
    public void findAllBySelect(){
        List<CodeDto> result = service040101.findAllBySelect(2);
        result.forEach(System.out::println);
    }
}
