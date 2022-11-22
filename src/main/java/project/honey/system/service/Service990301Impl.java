package project.honey.system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.system.dto.CodeDto;
import project.honey.system.dto.Tb901Dto;
import project.honey.system.dto.Tb906Dto;
import project.honey.system.entity.Tb901;
import project.honey.system.entity.Tb906;
import project.honey.system.repository.Tb906Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class Service990301Impl implements Service990301{

    private final Tb906Repository tb906Repository;

    @Override
    @Transactional
    public Integer insert(Tb906Dto dto) {
        return tb906Repository.save(Tb906Dto.toTb906(dto)).getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb906Dto dto) {
        Tb906 code = tb906Repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        code.changeInfo(dto);
        return code.getSeq();
    }

    @Override
    public Page<Tb906Dto> findAll(Pageable pageable, String fstId) {
        return tb906Repository.findAllByDsl(fstId, pageable).map(Tb906Dto::of);

    }

    @Override
    public Tb906Dto findById(Integer seq) {
        return Tb906Dto.of(tb906Repository.findById(seq).orElseThrow(RuntimeException::new));
    }

    @Override
    @Transactional
    public Integer delete(Integer seq) {
        tb906Repository.deleteById(seq);

        return seq;
    }

    // 대그룹으로 중그룹ID, 코드명 조회 메서드
    @Override
    public List<CodeDto> findByFstId(String fstId) {
        return tb906Repository.findByFstIdByDsl(fstId);
    }

    //대그룹 조회
    @Override
    public List<CodeDto> findFstIdAll() {
        return tb906Repository.findFstIdAllByDsl();
    }
}
