package project.honey.stock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.stock.dto.query.Query604Dto;
import project.honey.stock.dto.search.Search060109;
import project.honey.stock.dto.search.Search060110;
import project.honey.stock.dto.search.Search060111;
import project.honey.stock.entity.Tb604;

import java.util.List;

public interface Tb604RepositoryDsl {

    Page<Tb604> findAllByDsl(Search060110 search, Pageable pageable);

    List<Tb604> findAllByExcel(Search060110 search);

    Page<Query604Dto> findAllByQuery(Search060111 search, Pageable pageable);

    List<Query604Dto> findAllByQueryExcel(Search060111 search);
}
