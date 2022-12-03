package project.honey.personDepart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.honey.personDepart.entity.Tb201;

import java.util.List;
import java.util.Optional;

public interface Tb201Repository extends JpaRepository<Tb201, Integer>, Tb201RepositoryDsl {

    Optional<Tb201> findByEmpNo(String empNo);

    @Query("select t from Tb201 t where t.leaveDt = '' and t.leaveRs = ''")
    List<Tb201> findAllByWorking();
}
