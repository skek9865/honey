package project.honey.company.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.honey.company.entity.Tb104;

public interface Tb104Repository extends JpaRepository<Tb104, Integer> {
    Page<Tb104> findAll(Pageable pageable);

    @Query("select sum(limitamt) from Tb104")
    Integer getTotalLimitamt();
}
