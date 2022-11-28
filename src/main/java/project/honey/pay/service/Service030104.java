package project.honey.pay.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.pay.dto.PayrollDto;

public interface Service030104 {

    Page<PayrollDto> findAllByPay(Pageable pageable, String payDt, String empNm, String postCd, String deptCd);
}
