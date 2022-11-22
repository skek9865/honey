package project.honey.company.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.company.entity.Tb102;

public interface Tb102Repository extends JpaRepository<Tb102,Integer> {
    Page<Tb102> findAll(Pageable pageable);
}
