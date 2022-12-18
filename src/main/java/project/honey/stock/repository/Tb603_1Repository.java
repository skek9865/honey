package project.honey.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.stock.entity.Tb601_1;
import project.honey.stock.entity.Tb603_1;

public interface Tb603_1Repository extends JpaRepository<Tb603_1, Integer>, Tb603_1RepositoryDsl{

    @Query("select sum(t.qty) from Tb603_1 t where t.goodsCd =:goodsCd")
    Integer findQtyByGoodsCd(@Param("goodsCd") String goodsCd);
}
