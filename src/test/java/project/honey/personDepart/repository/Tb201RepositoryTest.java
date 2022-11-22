package project.honey.personDepart.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.honey.personDepart.entity.Tb201;

import java.util.Optional;

@SpringBootTest
public class Tb201RepositoryTest {

    @Autowired
    private Tb201Repository repository;

    @Test
    public void test(){
        Optional<Tb201> byEmpNo = repository.findByEmpNo("0001");
        System.out.println(byEmpNo.get());
    }
}
