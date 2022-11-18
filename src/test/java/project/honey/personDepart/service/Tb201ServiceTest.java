package project.honey.personDepart.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.honey.personDepart.dto.Tb201Dto;

@SpringBootTest
public class Tb201ServiceTest {

    @Autowired
    private Service020101 service;

    @Test
    public void save(){
        Tb201Dto dto = Tb201Dto.builder()
                .empNo   ("test")
                .empNm   ("test")
                .emp2Nm  ("test")
                .empEngNm("test")
                .idNo    ("test")
                .headYn  ("Y")
                .empDt   ("test")
                .empClass("test")
                .post    ("test")
                .post1   ("test")
                .leaveDt ("test")
                .leaveRs ("test")
                .phone   ("test")
                .mobile  ("test")
                .psNo    ("test")
                .email   ("test")
                .deptCd  ("test")
                .workCd  ("test")
                .bankNm  ("test")
                .aCutNo  ("test")
                .aCutNm  ("test")
                .zipCd   ("test")
                .address ("test")
                .address1("test")
                .picNm   ("test")
                .note    ("test")
                .fileNm  ("test")
                .createId("test")
                .updateId("test")
                .build();
        service.insert(dto);
    }

    @Test
    public void findAll(){
        service.findAll("6", "00003", "00008")
                .forEach(dto -> {
                    System.out.println("dto = " + dto);
                });
    }

    @Test
    public void findById(){
        Tb201Dto result = service.findById(1);
        System.out.println("dto : " + result);
    }

    @Test
    public void update(){
        Tb201Dto dto = Tb201Dto.builder()
                .seq(42)
                .empNo   ("test")
                .empNm   ("update")
                .emp2Nm  ("update")
                .empEngNm("update")
                .idNo    ("update")
                .headYn  ("Y")
                .empDt   ("test")
                .empClass("test")
                .post    ("test")
                .post1   ("test")
                .leaveDt ("test")
                .leaveRs ("test")
                .phone   ("test")
                .mobile  ("test")
                .psNo    ("test")
                .email   ("test")
                .deptCd  ("test")
                .workCd  ("test")
                .bankNm  ("test")
                .aCutNo  ("test")
                .aCutNm  ("test")
                .zipCd   ("test")
                .address ("test")
                .address1("test")
                .picNm   ("test")
                .note    ("test")
                .fileNm  ("test")
                .createId("test")
                .updateId("test")
                .build();
        service.update(dto);
    }

    @Test
    public void delete(){
        service.delete(42);
    }
}
