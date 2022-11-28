package project.honey.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.pay.entity.Tb303;

import java.util.List;
import java.util.Optional;

public interface Tb303Repository extends JpaRepository<Tb303, Integer> {

    @Query("select t from Tb303 t where t.empNo= :empNo and t.payDt = :payDt order by t.itemCd asc ")
    List<Tb303> findAllByEmpNoAndPayDt(@Param("empNo") String empNo,@Param("payDt") String payDt);

    void deleteAllByEmpNoAndPayDt(String empNo, String payDt);

    void deleteAllByPayDt(String payDt);
}
