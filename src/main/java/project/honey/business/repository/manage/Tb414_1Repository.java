package project.honey.business.repository.manage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.business.entity.manage.Tb414_1;

import java.util.List;

public interface Tb414_1Repository extends JpaRepository<Tb414_1, Integer> {

    @Query("select t from Tb414_1 t where t.tb414.seq = :fk")
    List<Tb414_1> findByFk(@Param("fk")Integer fk);

    @Modifying
    @Query("delete from Tb414_1 t where t.tb414.seq = :fk")
    void deleteByFk(@Param("fk")Integer fk);

    @Query("select t from Tb414_1 t where t.goodsCd = :goodsCd")
    List<Tb414_1> findSeqGoods(@Param("goodsCd") String goodsCd);
}
