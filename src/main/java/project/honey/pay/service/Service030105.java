package project.honey.pay.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.pay.dto.Dto030105;
import project.honey.pay.dto.PayrollDto;

public interface Service030105 {

    Page<Dto030105> findAll(Pageable pageable, String payDt, String empNm, String postCd, String deptCd);
}
