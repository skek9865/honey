package project.honey.business.repository.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.business.entity.sale.Tb415;

public interface Tb415Repository extends JpaRepository<Tb415, Integer>, Tb415RepositoryDsl {

    @Query("select count(t) from Tb415 t where t.orderDt = :orderDt")
    Long findOrderNo(@Param("orderDt")String orderDt);
}
