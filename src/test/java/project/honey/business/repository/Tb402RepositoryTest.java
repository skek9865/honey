package project.honey.business.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import project.honey.business.entity.manage.Tb412;
import project.honey.business.form.analyze.Search040301;
import project.honey.business.repository.manage.Tb412Repository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class Tb402RepositoryTest {

    @Autowired
    private Tb412Repository tb412Repository;

    @Test
    public void findAll(){
        List<String> custList = new ArrayList<>();
        Search040301 search040301 = new Search040301(null, null, null, null, null, null, null,null);
        Page<Tb412> result = tb412Repository.findAllBy040301("20020101", "20221231", search040301, custList, PageRequest.of(0, 50));
        result.forEach(System.out::println);
    }
}
