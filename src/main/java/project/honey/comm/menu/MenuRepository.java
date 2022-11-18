package project.honey.comm.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Tb904, Integer>,MenuRepositoryQueryDsl {

//    @Query("select Tb904.menuNm from Tb904 where Tb904 .fstId = :fstId")
//    List<Tb904> findMenuNm(@Param("fstId")String fstId);
}
