package project.honey.stock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.stock.dto.search.Search060101;
import project.honey.stock.dto.search.Search060102;
import project.honey.stock.entity.Tb601;
import project.honey.stock.entity.Tb602;

import java.util.List;

public interface Tb602RepositoryDsl {

    Page<Tb602> findAllByDsl(Search060102 search, Pageable pageable);

    List<Tb602> findAllByExcel(Search060102 search);
}
