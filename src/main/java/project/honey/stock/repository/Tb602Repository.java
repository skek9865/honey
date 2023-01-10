package project.honey.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.honey.stock.entity.Tb601;
import project.honey.stock.entity.Tb602;

public interface Tb602Repository extends JpaRepository<Tb602, Integer>, Tb602RepositoryDsl{

    @Query("select count(t) from Tb602 t where t.wHouseDt =:wHouseDt")
    int countByWHouseDt(String wHouseDt);
}
