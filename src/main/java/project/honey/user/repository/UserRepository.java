package project.honey.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import project.honey.user.entity.Tb901;

public interface UserRepository extends JpaRepository<Tb901, String> {

    boolean existsByUserIdAndPasswd(String userId, String passwd);
}

