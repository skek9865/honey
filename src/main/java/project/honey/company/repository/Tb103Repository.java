package project.honey.company.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.company.entity.Tb103;

public interface Tb103Repository extends JpaRepository<Tb103,Integer> {

    Page<Tb103> findAll(Pageable pageable);
}
