package project.honey.business.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.Search040105;
import project.honey.business.entity.Tb405;

import java.util.List;

public interface Tb405RepositoryDsl {

    Page<Tb405> findAllByDsl(Search040105 search, Pageable pageable);

    List<Tb405> findAllByExcel(Search040105 search);
}
