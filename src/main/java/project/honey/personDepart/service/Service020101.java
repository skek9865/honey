package project.honey.personDepart.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.personDepart.form.Tb201Form;
import project.honey.personDepart.dto.Tb201Dto;

import java.io.IOException;
import java.util.List;

public interface Service020101 {

    Boolean insert(Tb201Form form) throws IOException;

    Page<Tb201Dto> findAll(String empNm, String postCd, String deptCd, Pageable pageable);

    List<Tb201Dto> findAllByExcel(String empNm, String postCd, String deptCd);

    Tb201Dto findById(Integer id);

    Boolean update(Tb201Form form);

    Boolean delete(Integer id);

    Tb201Dto findByEmpNo(String empNo);
}
