package project.honey.business.repository.basic;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.business.entity.basic.Tb401;

public interface Tb401Repository extends JpaRepository<Tb401,Integer>, Tb401RepositoryDsl{
}
