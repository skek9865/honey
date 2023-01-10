package project.honey.produce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.entity.Tb501;
import project.honey.produce.entity.Tb502;

import java.util.List;

public interface Tb502RepositoryDsl {

    Page<Tb502> findAllByDsl(Pageable pageable);

    List<Tb502> findAllByExcel();
}
