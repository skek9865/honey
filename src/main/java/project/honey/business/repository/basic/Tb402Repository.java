package project.honey.business.repository.basic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.business.entity.basic.Tb402;

import java.util.List;

public interface Tb402Repository extends JpaRepository<Tb402, Integer>, Tb402RepositoryDsl {

    @Query("select t.custCd from Tb402 t where t.class1 = :class1")
    List<String> findAllByClass(@Param("class1")String class1);

    @Query("select t.custCd from Tb402 t where t.shipYn = :shipYn")
    List<String> findAllByShipYn(@Param("shipYn") String shipYn);
}
