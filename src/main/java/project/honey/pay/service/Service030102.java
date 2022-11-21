package project.honey.pay.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.pay.dto.Tb301Dto;
import project.honey.pay.dto.Tb302Dto;
import project.honey.pay.dto.Tb302ResultDto;

import java.util.List;

public interface Service030102 {

    Integer insert(Tb301Dto dto);

    Integer update(Tb301Dto dto);

    Page<Tb302ResultDto> findAllByLeave(Pageable pageable, String empNm, String postCd, String deptCd);

    Tb301Dto findById(Integer seq);

    void delete(Integer seq);
}
