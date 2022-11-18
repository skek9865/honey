package project.honey.personDepart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.personDepart.dto.Tb201Dto;
import project.honey.personDepart.entity.Tb201;
import project.honey.personDepart.repository.Tb201Repository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Service020101Impl implements Service020101 {

    private final Tb201Repository repository;

    @Override
    public void insert(Tb201Dto tb201Dto) {
        Tb201 entity = Tb201Dto.toTb201(tb201Dto);
        repository.save(entity);
    }

    @Override
    public Page<Tb201Dto> findAll(String empNm, String postCd, String deptCd, Pageable pageable) {
        Page<Tb201> result = repository.findAllByDsl(empNm, postCd, deptCd, pageable);
        Page<Tb201Dto> resultList = result.map(Tb201Dto::of);
        return resultList;
    }

    @Override
    public Tb201Dto findById(Integer id) {
        Tb201 entity = repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb201Dto.of(entity);
    }

    @Transactional
    @Override
    public void update(Tb201Dto dto) {
        Tb201 entity = repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        entity.updateData(dto);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
