package project.honey.business.repository.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.business.entity.sale.Tb416;

public interface Tb416Repository extends JpaRepository<Tb416, Integer>,Tb416RepositoryDsl {

    @Query("select count(t) from Tb416 t where t.buyDt = :buyDt")
    Long findBuyNo(@Param("buyDt")String buyDt);
}
