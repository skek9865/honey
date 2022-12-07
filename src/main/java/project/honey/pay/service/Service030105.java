package project.honey.pay.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.pay.dto.Dto030105;
import project.honey.pay.dto.PayrollDto;
import project.honey.pay.dto.PrintData030105;

public interface Service030105 {

    Page<Dto030105> findAll(Pageable pageable, String payDt, String empNm, String postCd, String deptCd);

    PrintData030105 print(String empNo, String payDt);
}
