package project.honey.produce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.honey.produce.entity.Tb503;
import project.honey.produce.entity.Tb503_1;

import java.util.List;

public interface Tb503_1Repository extends JpaRepository<Tb503_1, Integer>, Tb503_1RepositoryDsl {

    @Query("select t from Tb503_1 t join fetch t.tb503")
    List<Tb503_1> findAllByExcel();
}
