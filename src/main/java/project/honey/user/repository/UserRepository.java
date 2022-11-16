package project.honey.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.user.entity.Tb901;

public interface UserRepository extends JpaRepository<Tb901, String> {
}
