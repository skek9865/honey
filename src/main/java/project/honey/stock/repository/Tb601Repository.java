package project.honey.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.honey.stock.entity.Tb601;

public interface Tb601Repository extends JpaRepository<Tb601, Integer>, Tb601RepositoryDsl{

    @Query("select count(t) from Tb601 t where t.wHouseDt =:wHouseDt")
    int countByWHouseDt(String wHouseDt);
}
