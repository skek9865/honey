package project.honey.produce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.Query506_1Dto;
import project.honey.produce.dto.Tb505_1Dto;
import project.honey.produce.dto.Tb506_1Dto;
import project.honey.stock.dto.search.Search060109;

import java.util.List;

public interface Tb506_1RepositoryDsl {

    Page<Query506_1Dto> findAllByQuery(Search060109 search, Pageable pageable);

    List<Query506_1Dto> findAllByExcel(Search060109 search);

    List<Tb506_1Dto> findAllByDtos(Integer fk);
}
