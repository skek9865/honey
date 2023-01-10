package project.honey.personDepart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.honey.company.entity.Tb101;
import project.honey.company.repository.Tb101Repository;
import project.honey.personDepart.dto.PrintData020301;
import project.honey.personDepart.dto.PrintData020302;
import project.honey.personDepart.entity.Tb201;
import project.honey.personDepart.repository.Tb201Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class Service020302Impl implements Service020302{

    private final Tb201Repository tb201Repository;
    private final Tb101Repository tb101Repository;

    @Override
    public PrintData020302 getData(String empNo) {
        Tb201 emp = tb201Repository.findByEmpNo(empNo).orElseThrow(RuntimeException::new);

        String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now());
        String address = emp.getAddress() + " " + emp.getAddress1();
        Tb101 comp = tb101Repository.findById(27).orElseThrow(RuntimeException::new);

        PrintData020302 result = PrintData020302.builder()
                .date(date)
                .name(emp.getEmpNm())
                .idNo(emp.getIdNo())
                .address(address)
                .compNm(comp.getCorpnm())
                .compIdNo(comp.getCorpno())
                .deptNm(emp.getDeptCd())
                .post(emp.getPost())
                .empDt(emp.getEmpDt())
                .phone(emp.getPhone())
                .workNm(emp.getWorkCd())
                .logoFNm(comp.getLogonm())
                .stampFNm(comp.getStampnm())
                .build();

        return result;
    }
}
