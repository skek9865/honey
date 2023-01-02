package project.honey.business.repository.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.business.entity.sale.Tb416_1;

import java.util.List;

public interface Tb416_1Repository extends JpaRepository<Tb416_1, Integer> {

    @Query("select t from Tb416_1 t where t.tb416.seq = :fk")
    List<Tb416_1> findByFk(@Param("fk")Integer fk);

    @Modifying
    @Query("delete from Tb416_1 t where t.tb416.seq = :fk")
    void deleteByFk(@Param("fk")Integer fk);

    @Query("select t from Tb416_1 t where t.goodsCd = :goodsCd")
    List<Tb416_1> findSeqGoods(@Param("goodsCd") String goodsCd);
}
