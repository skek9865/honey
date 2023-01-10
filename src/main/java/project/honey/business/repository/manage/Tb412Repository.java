package project.honey.business.repository.manage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.business.entity.manage.Tb412;

public interface Tb412Repository extends JpaRepository<Tb412, Integer>,Tb412RepositoryDsl {
    @Query("select count(t) from Tb412 t where t.saleDt = :saleDt")
    Long findSaleNo(@Param("saleDt")String saleDt);

    @Query("select sum(t1.amtSum) from Tb412 t, Tb412_1 t1 where t1.tb412.seq = :fk and t.custCd = :custCd")
    Integer sumAmtVat(@Param("custCd")String custCd, @Param("fk")Integer fk);
}
