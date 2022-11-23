package project.honey.personDepart.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import project.honey.personDepart.dto.Form020101;
import project.honey.personDepart.dto.Tb201Dto;

import java.io.IOException;

@SpringBootTest
public class Tb201ServiceTest {

    @Autowired
    private Service020101 service;

    @Test
    public void save() throws IOException {
        Form020101 form = Form020101.builder()
                .seq(100)
                .empNo("111")
                .empNm("111")
                .headYn(false)
                .empClass("00")
                .build();
        service.insert(form);
    }

    @Test
    public void findAll(){
        service.findAll("6", "00003", "00008", PageRequest.of(0,10))
                .forEach(dto -> {
                    System.out.println("dto = " + dto);
                });
    }

    @Test
    public void findAllByExcel(){
        service.findAllByExcel(null, null, null).forEach(System.out::println);
    }

    @Test
    public void findById(){
        Tb201Dto result = service.findById(1);
        System.out.println("dto : " + result);
    }

//    @Test
//    public void update(){
//        Tb201Dto dto = Tb201Dto.builder()
//                .seq(42)
//                .empNo   ("test")
//                .empNm   ("update")
//                .emp2Nm  ("update")
//                .empEngNm("update")
//                .idNo    ("update")
//                .headYn  ("Y")
//                .empDt   ("test")
//                .empClass("test")
//                .post    ("test")
//                .post1   ("test")
//                .leaveDt ("test")
//                .leaveRs ("test")
//                .phone   ("test")
//                .mobile  ("test")
//                .psNo    ("test")
//                .email   ("test")
//                .deptCd  ("test")
//                .workCd  ("test")
//                .bankNm  ("test")
//                .aCutNo  ("test")
//                .aCutNm  ("test")
//                .zipCd   ("test")
//                .address ("test")
//                .address1("test")
//                .picNm   ("test")
//                .note    ("test")
//                .fileNm  ("test")
//                .createId("test")
//                .updateId("test")
//                .build();
//        service.update(dto);
//    }

    @Test
    public void delete(){
        service.delete(42);
    }
}
