package project.honey.business.repository.sale;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.business.entity.sale.Tb417;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface Tb417Repository extends JpaRepository<Tb417, Integer>, Tb417RepositoryDsl {

    @Query("select sum(t.amount) from Tb417 t where t.custCd = :custCd and t.amountCl = '00001'")
    Integer sumAmountByCust(@Param("custCd") String custCd);

    @Query("select sum(t.amount) from Tb417 t where t.custCd = :custCd and t.amountCl = '00002'")
    Integer sumOutAmountByCust(@Param("custCd") String custCd);

    @Query("select t from Tb417 t where t.amountDt between :ymd1 and :ymd2 and t.amountCl = '00001'")
    List<Tb417> findAllBy040308(@Param("ymd1") String ymd1, @Param("ymd2") String ymd2, Pageable pageable);

    @Query("select count(t) from Tb417 t where t.amountDt between :ymd1 and :ymd2 and t.amountCl = '00001'")
    Integer count040308(@Param("ymd1") String ymd1, @Param("ymd2") String ymd2);

    @Query("select t from Tb417 t where t.amountDt between :ymd1 and :ymd2 and t.amountCl = '00001'")
    List<Tb417> findAllBy040308Excel(@Param("ymd1") String ymd1, @Param("ymd2") String ymd2);
}
