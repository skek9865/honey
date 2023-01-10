package project.honey.personDepart.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.personDepart.form.Tb202Form;
import project.honey.personDepart.dto.Tb202Dto;
import project.honey.system.dto.CodeDto;

import java.util.List;

public interface Service020102 {

    Boolean insert(Tb202Form form);

    Page<Tb202Dto> findAll(Pageable pageable);

    Tb202Dto findById(Integer id);

    Boolean update(Tb202Form form);

    Boolean delete(Integer id);

    List<CodeDto> findAllDept();

    List<List<String>> findAllByExcel();

    String findDeptNmByDeptCd(String deptCd);
}
