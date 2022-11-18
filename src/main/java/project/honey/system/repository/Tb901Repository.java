package project.honey.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.system.entity.Tb901;

public interface Tb901Repository extends JpaRepository<Tb901, String> {

    boolean existsByUserIdAndPasswd(String userId, String passwd);
}

