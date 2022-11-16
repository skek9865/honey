package project.honey.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.user.entity.Tb902;

public interface HistoryOfUseRepository extends JpaRepository<Tb902, Integer> {
}
