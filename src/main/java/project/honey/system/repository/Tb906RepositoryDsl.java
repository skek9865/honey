package project.honey.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.system.dto.CodeDto;
import project.honey.system.entity.Tb906;

import java.util.List;

public interface Tb906RepositoryDsl {

    Page<Tb906> findAllByDsl(String fstId, Pageable pageable);

    List<CodeDto> findByFstIdByDsl(String fstId);
}
