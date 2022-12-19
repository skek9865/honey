package project.honey.business.repository.manage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.business.entity.manage.Tb411_1;

import java.util.List;

public interface Tb411_1Repository extends JpaRepository<Tb411_1, Integer> {

    @Query("select t from Tb411_1 t where t.tb411.seq = :fk")
    List<Tb411_1> findByFk(@Param("fk")Integer fk);

    @Modifying
    @Query("delete from Tb411_1 t where t.tb411.seq = :fk")
    void deleteByFk(@Param("fk")Integer fk);

    @Query("select t from Tb411_1 t where t.goodsCd = :goodsCd")
    List<Tb411_1> findSeqGoods(@Param("goodsCd") String goodsCd);
}
