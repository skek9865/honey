package project.honey.personDepart.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import project.honey.personDepart.dto.Tb201Dto;

import java.util.List;

public interface Service020101 {

    void insert(Tb201Dto tb201Dto);

    Page<Tb201Dto> findAll(String empNm, String postCd, String deptCd, Pageable pageable);

    Tb201Dto findById(Integer id);

    void update(Tb201Dto dto);

    void delete(Integer id);
}
