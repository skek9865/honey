package project.honey.company.service;

import org.springframework.data.domain.Page;
import project.honey.company.dto.Tb102Dto;

import java.awt.print.Pageable;

public interface Service010201 {
    //통장 등록
    void save(Tb102Dto dto);

    //모든 통장 가져오기
    Page<Tb102Dto> findAll(Pageable pageable);
}
