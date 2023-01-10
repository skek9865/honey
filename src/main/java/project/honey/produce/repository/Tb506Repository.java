package project.honey.produce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.honey.produce.entity.Tb505;
import project.honey.produce.entity.Tb506;

public interface Tb506Repository extends JpaRepository<Tb506, Integer>, Tb506RepositoryDsl {

    @Query("select count(t) from Tb506 t where t.wHouseDt =:wHouseDt")
    int countByWHouseDt(String wHouseDt);
}
