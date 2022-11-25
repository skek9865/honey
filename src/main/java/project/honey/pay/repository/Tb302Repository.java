package project.honey.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.pay.entity.Tb302;

import java.util.List;

public interface Tb302Repository extends JpaRepository<Tb302, Integer> {

    List<Tb302> findAllByEmpNoOrderByItemCdAsc(String empNo);

    void deleteAllByEmpNo(String empNo);
}
