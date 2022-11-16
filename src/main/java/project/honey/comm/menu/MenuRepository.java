package project.honey.comm.menu;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Tb904, Integer>,MenuRepositoryQueryDsl {
}
