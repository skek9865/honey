package project.honey.personDepart.service;

import project.honey.system.dto.CodeDto;

import java.util.List;

public interface Service020102 {

    List<CodeDto> findAllDept();
    String findDeptNmByDeptCd(String deptCd);
}
