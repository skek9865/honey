package project.honey.personDepart.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import project.honey.personDepart.dto.Tb202Dto;

@SpringBootTest
public class Service020102Test {

    @Autowired
    private Service020102 service020102;

//    @Test
//    public void insert(){
//        Tb202Dto dto = Tb202Dto.builder()
//                        .deptCd("aaa")
//                                .deptNm("aaa")
//                                        .useYn(true)
//                                                .createId("aa")
//                                                        .createDate("20220101")
//                .updateId("aa")
//                .updateDate("20220101")
//                                                                .build();
//        Assertions.assertThat(service020102.insert(dto).booleanValue());
//    }

    @Test
    public void findAll(){
        Page<Tb202Dto> resultList = service020102.findAll(PageRequest.of(0, 50));
        resultList.forEach(System.out::println);

    }

    @Test
    public void findById(){
        Tb202Dto byId = service020102.findById(3);
        System.out.println(byId);
    }

    @Test
    public void findAllDept(){
        service020102.findAllDept().forEach(System.out::println);
    }
}
