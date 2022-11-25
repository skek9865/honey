package project.honey.pay.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.pay.dto.Tb301Dto;
import project.honey.pay.dto.Tb302Dto;
import project.honey.pay.dto.Tb302PopupDto;
import project.honey.pay.dto.Tb302HomeDto;

import java.util.List;

public interface Service030102 {

    Integer insert(Tb301Dto dto);

    Integer update(Tb301Dto dto);

    List<Tb302PopupDto> findAll(String empNo);

    Page<Tb302HomeDto> findAllByLeave(Pageable pageable, String empNm, String postCd, String deptCd);

    Tb302Dto findById(Integer seq);

    Integer delete(Integer seq);

    String pitemeSaveOne(String empNo);

    String pitemeDelOne(String empNo);

    String pitemeSave();

    String pitemeDel();
}
