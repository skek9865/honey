package project.honey.produce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.honey.produce.entity.Tb505;

public interface Tb505Repository extends JpaRepository<Tb505, Integer>, Tb505RepositoryDsl {

    @Query("select count(t) from Tb505 t where t.wHouseDt =:wHouseDt")
    int countByWHouseDt(String wHouseDt);
}
