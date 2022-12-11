package project.honey.produce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.Tb504_1Dto;
import project.honey.produce.dto.search.Search050201;
import project.honey.produce.entity.Tb504;
import project.honey.produce.entity.Tb504_1;

import java.util.List;

public interface Tb504_1RepositoryDsl {

    Page<Tb504_1> findAllByDsl(Search050201 search, Pageable pageable);

    List<Tb504_1Dto> findAllByDtos(Integer fk);
}
