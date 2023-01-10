package project.honey.business.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.entity.manage.Tb410;
import project.honey.business.entity.manage.Tb410_1;
import project.honey.business.form.manage.Search040201;
import project.honey.business.repository.manage.Tb410Repository;
import project.honey.business.repository.manage.Tb410_1Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class Tb410RepositoryTest {

    @Autowired
    private Tb410Repository tb410Repository;

    @Autowired
    private Tb410_1Repository tb410_1Repository;

    @Test
    public void findById(){
        Tb410 tb410 = tb410Repository.findById(18).orElseThrow(RuntimeException::new);
        List<Tb410_1> byFk = tb410_1Repository.findByFk(tb410.getSeq());
        byFk.forEach(System.out::println);
    }

    @Test
    public void findAll(){
        Search040201 search040201 = new Search040201(
                "20020101", "20221212",null,null,
                null,null,null,null);
        Page<Tb410> result = tb410Repository.findAllByDsl("20020101", "20221212", search040201,
                new ArrayList<>(), PageRequest.of(0, 50));
        result.forEach(System.out::println);
    }

    @Test
    public void findAllByExcel(){
        Search040201 search040201 = new Search040201(
                "20020101", "20221212",null,null,
                null,null,null,null);
        List<Tb410> result = tb410Repository.findAllByExcel("20020101", "20221212", search040201, new ArrayList<>());
        result.forEach(System.out::println);
    }
}
