package project.honey.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.system.entity.Tb904;

public interface Tb904Repository extends JpaRepository<Tb904, Integer>, Tb904RepositoryDsl {

//    @Query("select Tb904.menuNm from Tb904 where Tb904 .fstId = :fstId")
//    List<Tb904> findMenuNm(@Param("fstId")String fstId);
}
