package project.honey.business.repository.manage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.business.entity.manage.Tb412_1;
import project.honey.business.entity.manage.Tb413_1;

import java.util.List;

public interface Tb413_1Repository extends JpaRepository<Tb413_1, Integer> {
    @Query("select t from Tb413_1 t where t.tb413.seq = :fk")
    List<Tb413_1> findByFk(@Param("fk")Integer fk);

    @Modifying
    @Query("delete from Tb413_1 t where t.tb413.seq = :fk")
    void deleteByFk(@Param("fk")Integer fk);

    @Query("select t from Tb413_1 t where t.goodsCd = :goodsCd")
    List<Tb413_1> findSeqGoods(@Param("goodsCd") String goodsCd);
}
