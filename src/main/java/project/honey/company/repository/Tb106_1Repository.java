package project.honey.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.company.entity.Tb106_1;

import java.util.List;

public interface Tb106_1Repository extends JpaRepository<Tb106_1, Integer> {

    @Query("select t from Tb106_1 t where t.tb106 = :fk")
    List<Tb106_1> findAllByFk(@Param("fk")Integer fk);
}
