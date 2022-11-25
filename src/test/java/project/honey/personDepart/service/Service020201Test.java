package project.honey.personDepart.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import project.honey.personDepart.dto.Tb203Dto;
import project.honey.personDepart.form.Tb203Form;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@SpringBootTest
public class Service020201Test {

    @Autowired
    private Service020201 service020201;

    @Qualifier("httpSession")
    @Autowired
    private HttpSession session;

    @Test
    public void insert() throws IOException {
        session.setAttribute("user", "hello");

        Tb203Form form = Tb203Form.builder()
                        .empNo("aaa")
                                .note("aaa")
                                        .part("aaa")
                                                .build();

        service020201.insert(form);
    }

    @Test
    public void findAll(){
        session.setAttribute("user", "hello");

        Page<Tb203Dto> result = service020201.findAll(null, null, PageRequest.of(0, 50));
        result.forEach(System.out::println);
    }

    @Test
    public void findById(){
        Tb203Dto byId = service020201.findById(6);
        System.out.println(byId);
    }

    @Test
    public void update(){
        session.setAttribute("user", "hello");

        Tb203Form form = Tb203Form.builder()
                .seq(12)
                        .empNo("test")
                                .part("test")
                                        .note("aaa")
                                                .build();
        service020201.update(form);
    }

    @Test
    public void delete(){
        session.setAttribute("user", "hello");

        service020201.delete(12);
    }

    @Test
    public void sessionTest(){
        System.out.println(session.getAttribute("user"));
    }
}
