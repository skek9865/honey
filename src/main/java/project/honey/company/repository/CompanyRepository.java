package project.honey.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.company.entity.Tb101;

public interface CompanyRepository extends JpaRepository<Tb101, Integer> {
}
