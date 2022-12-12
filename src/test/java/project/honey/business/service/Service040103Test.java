package project.honey.business.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.honey.system.dto.CodeDto;

import java.util.List;

@SpringBootTest
public class Service040103Test {

    @Autowired
    private Service040103 service040103;

    @Test
    public void select(){
        List<CodeDto> result = service040103.findAllBySelect();
        result.forEach(System.out::println);
    }
}
