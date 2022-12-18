package project.honey.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.honey.stock.entity.Tb604;

public interface Tb604Repository extends JpaRepository<Tb604, Integer>, Tb604RepositoryDsl {

    @Query("select count(t) from Tb604 t where t.wHouseDt =:wHouseDt")
    int countByWHouseDt(String wHouseDt);
}
