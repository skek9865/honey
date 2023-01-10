package project.honey.business.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import project.honey.business.entity.manage.Tb411;
import project.honey.business.form.analyze.Search040302;
import project.honey.business.form.analyze.Search040304;
import project.honey.business.repository.manage.Tb411Repository;

@SpringBootTest
public class Tb411RepositoryTest {

    @Autowired
    private Tb411Repository tb411Repository;

    @Test
    public void findAll(){
        Search040302 search040302 = new Search040302(null, null, null, null, "100", null, null);
        Page<Tb411> result = tb411Repository.findAllBy040303("20020101", "20221231", search040302, PageRequest.of(0, 50));
        result.forEach(System.out::println);
    }
}
