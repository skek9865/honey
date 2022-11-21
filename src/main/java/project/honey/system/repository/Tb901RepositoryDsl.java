package project.honey.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.system.dto.Tb901Dto;
import project.honey.system.entity.Tb906;

public interface Tb901RepositoryDsl {

    // Dto로 바로 조회
    Page<Tb901Dto> findAllByDsl(Pageable pageable);
}
