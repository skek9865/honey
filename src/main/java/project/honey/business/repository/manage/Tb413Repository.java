package project.honey.business.repository.manage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.business.entity.manage.Tb413;

public interface Tb413Repository extends JpaRepository<Tb413, Integer>, Tb413RepositoryDsl {
    @Query("select count(t) from Tb413 t where t.shipDt = :shipDt")
    Long findShipNo(@Param("shipDt")String shipDt);
}
