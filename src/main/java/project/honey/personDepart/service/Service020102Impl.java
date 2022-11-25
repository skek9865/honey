package project.honey.personDepart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.personDepart.form.Tb202Form;
import project.honey.personDepart.dto.Tb202Dto;
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
    public Boolean insert(Tb202Form form) {
        Tb202 entity = Tb202Form.toTb202(form);
        tb202Repository.save(entity);
        return true;
    }

    @Override
    public Page<Tb202Dto> findAll(Pageable pageable) {
        Page<Tb202> result = tb202Repository.findAll(pageable);
        Page<Tb202Dto> resultList = result.map(Tb202Dto::of);
        return resultList;
    }

    @Override
    public Tb202Dto findById(Integer id) {
        Tb202 entity = tb202Repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb202Dto.of(entity);
    }

    @Transactional
    @Override
    public Boolean update(Tb202Form form) {
        Tb202 entity = tb202Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);
        entity.updateData(form);
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        tb202Repository.deleteById(id);
        return true;
    }

    @Override
    public List<Tb202Dto> findAllByExcel() {
        List<Tb202> result = tb202Repository.findAll();
        return result.stream().map(Tb202Dto::of).collect(Collectors.toList());
    }

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
