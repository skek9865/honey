package project.honey.business.repository.manage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.business.entity.manage.Tb410;

import java.util.List;

public interface Tb410Repository extends JpaRepository<Tb410,Integer>, Tb410RepositoryDsl {
    @Query("select count(t) from Tb410 t where t.estimDt = :estDt")
    Long findEstNo(@Param("estDt")String estDt);
}
