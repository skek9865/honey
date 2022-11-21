package project.honey.personDepart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.personDepart.entity.Tb202;

import java.util.Optional;

public interface Tb202Repository extends JpaRepository<Tb202, Integer> {
    Optional<Tb202> findByDeptCd(String deptCd);
}
