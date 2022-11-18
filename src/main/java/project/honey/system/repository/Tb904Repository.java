package project.honey.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.honey.system.entity.Tb904;

import java.util.List;

public interface Tb904Repository extends JpaRepository<Tb904, Integer>, Tb904RepositoryDsl {

    @Query("select t from Tb904 t where t.fstId = :fstId")
    List<Tb904> findMenuNm(@Param("fstId")String fstId);
}
