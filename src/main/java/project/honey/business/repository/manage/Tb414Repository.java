package project.honey.business.repository.manage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.business.entity.manage.Tb414;

public interface Tb414Repository extends JpaRepository<Tb414, Integer>, Tb414RepositoryDsl {
    @Query("select count(t) from Tb414 t where t.shipDt = :shipDt")
    Long findShipNo(@Param("shipDt")String shipDt);
}
