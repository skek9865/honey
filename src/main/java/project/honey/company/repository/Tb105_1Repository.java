package project.honey.company.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.company.entity.Tb105_1;
import project.honey.system.dto.CodeDto;


public interface Tb105_1Repository extends JpaRepository<Tb105_1, Integer> {

    Page<Tb105_1> findAll(Pageable pageable);

    @Query("select sum(t.instamt) from Tb105_1 t where t.seq=:id")
    Integer getTotalInstamt(@Param("id") Integer id);


}
