package project.honey.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.user.entity.Tb903;

public interface UserRoleRepository extends JpaRepository<Tb903, Integer> {
}
