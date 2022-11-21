package project.honey.personDepart.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import project.honey.personDepart.dto.Form020101;
import project.honey.personDepart.dto.Tb201Dto;

import java.io.IOException;
import java.util.List;

public interface Service020101 {

    void insert(Form020101 form) throws IOException;

    Page<Tb201Dto> findAll(String empNm, String postCd, String deptCd, Pageable pageable);

    Tb201Dto findById(Integer id);

    void update(Tb201Dto dto);

    void delete(Integer id);
}
