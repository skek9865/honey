package project.honey.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.honey.stock.entity.Tb602;
import project.honey.stock.entity.Tb603;

public interface Tb603Repository extends JpaRepository<Tb603, Integer>, Tb603RepositoryDsl{

    @Query("select count(t) from Tb603 t where t.wHouseDt =:wHouseDt")
    int countByWHouseDt(String wHouseDt);
}
