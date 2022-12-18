package project.honey.business.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import project.honey.business.dto.manage.Tb410Dto;
import project.honey.business.dto.manage.Tb410MainDto;
import project.honey.business.dto.manage.Tb410_1Dto;
import project.honey.business.form.manage.Search040201;
import project.honey.business.service.manage.Service040201;

import java.util.List;

@SpringBootTest
public class Service040201Test {

    @Autowired
    private Service040201 service040201;

    @Test
    public void findAll(){
        Search040201 search040201 = new Search040201(
                "20020101", "20221212",null,null,
        null,null,null,null);
        Page<Tb410MainDto> result =
                service040201.findAllByDsl
                        (search040201, PageRequest.of(0, 50));
        result.forEach(System.out::println);
    }

    @Test
    public void findAllByExcel(){
        Search040201 search040201 = new Search040201(
                "20020101", "20221212",null,null,
                null,null,null,null);
        List<List<String>> resultList = service040201.findAllByExcel(search040201);
        resultList.forEach(System.out::println);
    }

    @Test
    public void findById(){
        Tb410Dto result = service040201.findById(18);
        System.out.println(result);
    }

    @Test
    public void findByChild(){
        List<Tb410_1Dto> childByFk = service040201.findChildByFk(18);
        childByFk.forEach(System.out::println);
    }
}
