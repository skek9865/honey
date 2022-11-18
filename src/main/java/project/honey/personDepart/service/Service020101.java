package project.honey.personDepart.service;

import org.springframework.transaction.annotation.Transactional;
import project.honey.personDepart.dto.Tb201Dto;

import java.util.List;

public interface Service020101 {

    void insert(Tb201Dto tb201Dto);

    List<Tb201Dto> findAll(String empNm, String postCd, String deptCd);

    Tb201Dto findById(Integer id);

    void update(Tb201Dto dto);

    void delete(Integer id);
}
