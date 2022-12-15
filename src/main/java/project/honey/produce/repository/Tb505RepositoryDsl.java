package project.honey.produce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.search.Search050301;
import project.honey.produce.entity.Tb505;

import java.util.List;

public interface Tb505RepositoryDsl {

    Page<Tb505> findAllByDsl(Search050301 search, Pageable pageable);

    List<Tb505> findAllByExcel(Search050301 search);
}
