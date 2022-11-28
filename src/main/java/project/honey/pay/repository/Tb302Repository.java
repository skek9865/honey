package project.honey.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.pay.entity.Tb302;

import java.util.List;
import java.util.Optional;

public interface Tb302Repository extends JpaRepository<Tb302, Integer> {

    List<Tb302> findAllByEmpNoOrderByItemCdAsc(String empNo);

    boolean existsByEmpNo(String empNo);

    void deleteAllByEmpNo(String empNo);

    Optional<Tb302> findByEmpNoAndItemCd(String empNo, String itemCd);
}
