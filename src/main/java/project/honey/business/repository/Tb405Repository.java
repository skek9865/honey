package project.honey.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.business.entity.Tb405;

import java.util.Optional;

public interface Tb405Repository extends JpaRepository<Tb405, Integer>, Tb405RepositoryDsl {
    Optional<Tb405> findByGoodsCd(String goodsCd);
}
