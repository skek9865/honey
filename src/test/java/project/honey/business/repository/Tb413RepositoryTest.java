package project.honey.business.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import project.honey.business.entity.manage.Tb413;
import project.honey.business.form.analyze.Search040304;
import project.honey.business.repository.manage.Tb413Repository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class Tb413RepositoryTest {

    @Autowired
    private Tb413Repository tb413Repository;

    @Test
    public void findAll(){
        Search040304 search040304 = new Search040304(null, null, null, 1, null, null, null, null);
        Page<Tb413> result = tb413Repository.findAllBy040304("20020101", "20221231", search040304, PageRequest.of(0, 50));
        result.forEach(System.out::println);
    }

    @Test
    public void findAllByExcel(){
        Search040304 search040304 = new Search040304(null, null, null, null, null, null, "00028", null);
        List<Tb413> result = tb413Repository.findAllBy040304Excel("20020101", "20221231", search040304);
        result.forEach(System.out::println);
    }
}
