package project.honey.business.repository.manage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.business.entity.manage.Tb410_1;

import java.util.List;

public interface Tb410_1Repository extends JpaRepository<Tb410_1,Integer>, Tb410_1RepositoryDsl {

    @Query("select t from Tb410_1 t where t.tb410.seq = :fk")
    List<Tb410_1> findByFk(@Param("fk")Integer fk);

    @Modifying
    @Query("delete from Tb410_1 t where t.tb410.seq = :fk")
    void deleteByFk(@Param("fk")Integer fk);

    @Query("select t from Tb410_1 t where t.goodsCd = :goodsCd")
    List<Tb410_1> findSeqGoods(@Param("goodsCd") String goodsCd);
}
