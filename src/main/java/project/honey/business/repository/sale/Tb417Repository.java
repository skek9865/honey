package project.honey.business.repository.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.business.entity.sale.Tb417;

public interface Tb417Repository extends JpaRepository<Tb417, Integer> {

    @Query("select sum(t.amount) from Tb417 t where t.custCd = :custCd and t.amountCl = '00001'")
    Integer sumAmountByCust(@Param("custCd") String custCd);
}
