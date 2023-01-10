package project.honey.business.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.honey.business.entity.manage.Tb412;
import project.honey.business.form.analyze.Search040307;
import project.honey.business.repository.manage.Tb412Repository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class Tb412RepositoryTest {

    @Autowired
    private Tb412Repository tb412Repository;

    @Test
    public void r040307(){
        List<String> custList = new ArrayList<>();
        List<String> shipList = new ArrayList<>();
        Search040307 search040307 = new Search040307(null, null, null, null, null, null, null, null);
        List<Tb412> result = tb412Repository.findAllBy040307("20020101", "20221231", search040307, custList, shipList);
        result.forEach(System.out::println);
    }

    @Test
    public void sum(){
        Integer integer = tb412Repository.sumAmtVat("00104", 1454);
        System.out.println(integer);
    }
}
