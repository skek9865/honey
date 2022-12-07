package project.honey.produce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.entity.Tb501;

import java.util.List;

public interface Tb501RepositoryDsl {

    Page<Tb501> findAllByDsl(Pageable pageable);

    List<Tb501> findAllByExcel();
}
