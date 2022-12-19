package project.honey.produce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.Tb503_1Dto;
import project.honey.produce.entity.Tb503_1;

import java.util.List;

public interface Tb503_1RepositoryDsl {

    Page<Tb503_1> findAllByDsl(Pageable pageable);

    List<Tb503_1Dto> findAllByDtos(Integer fk);
}
