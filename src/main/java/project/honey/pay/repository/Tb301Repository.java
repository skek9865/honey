package project.honey.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.pay.entity.Tb301;

import java.util.Optional;

public interface Tb301Repository extends JpaRepository<Tb301, Integer>, Tb301RepositoryDsl {
    Optional<Tb301> findByItemCd(String itemCd);
}
