package project.honey.business.repository.basic;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.business.entity.basic.Tb405;

import java.util.Optional;

public interface Tb405Repository extends JpaRepository<Tb405, Integer>, Tb405RepositoryDsl {
    Optional<Tb405> findByGoodsCd(String goodsCd);
}
