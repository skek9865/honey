package project.honey.business.repository.manage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.business.entity.manage.Tb411;

public interface Tb411Repository extends JpaRepository<Tb411, Integer>, Tb411RepositoryDsl {
    @Query("select count(t) from Tb411 t where t.orderDt = :orderDt")
    Long findOrderNo(@Param("orderDt")String orderDt);
}
