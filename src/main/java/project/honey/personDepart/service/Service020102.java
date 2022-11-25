package project.honey.personDepart.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.personDepart.dto.Form020102;
import project.honey.personDepart.dto.Tb202Dto;
import project.honey.system.dto.CodeDto;

import java.util.List;

public interface Service020102 {

    Boolean insert(Form020102 form);

    Page<Tb202Dto> findAll(Pageable pageable);

    Tb202Dto findById(Integer id);

    Boolean update(Form020102 form);

    Boolean delete(Integer id);

    List<CodeDto> findAllDept();

    List<Tb202Dto> findAllByExcel();

    String findDeptNmByDeptCd(String deptCd);
}
