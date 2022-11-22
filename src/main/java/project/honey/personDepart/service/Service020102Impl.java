package project.honey.personDepart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.honey.personDepart.entity.Tb202;
import project.honey.personDepart.repository.Tb202Repository;
import project.honey.system.dto.CodeDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Service020102Impl implements Service020102{

    private final Tb202Repository tb202Repository;

    @Override
    public List<CodeDto> findAllDept() {
        List<Tb202> findList = tb202Repository.findAll();
        List<CodeDto> resultList = findList.stream().map
                (entity -> (CodeDto.builder().text(entity.getDeptNm()).value(entity.getDeptCd()).build())).collect(Collectors.toList());
        return resultList;
    }

    @Override
    public String findDeptNmByDeptCd(String deptCd) {
        Tb202 result = tb202Repository.findByDeptCd(deptCd).orElseThrow(IllegalArgumentException::new);
        return result.getDeptNm();
    }
}
