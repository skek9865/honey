package project.honey.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.honey.system.entity.Tb903;
import project.honey.system.entity.Tb905;

import java.util.List;

public interface Tb905Repository extends JpaRepository<Tb905, Integer>, Tb905RepositoryDsl {

    @Query("select t from Tb905 t order by t.tpId asc")
    List<Tb905> findAllByExcel();
}
