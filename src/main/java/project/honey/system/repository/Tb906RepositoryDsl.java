package project.honey.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.system.dto.CodeDto;
import project.honey.system.entity.Tb906;

import java.util.List;

public interface Tb906RepositoryDsl {

    Page<Tb906> findAllByDsl(String fstId, Pageable pageable);

    //대그룹으로 중그룹, 코드명 검색
    List<CodeDto> findByFstIdByDsl(String fstId);

    // 대그룹, 코드명 검색
    List<CodeDto> findFstIdAllByDsl();
}
