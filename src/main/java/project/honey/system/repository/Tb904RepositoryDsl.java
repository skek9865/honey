package project.honey.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.system.entity.Tb904;

import java.util.List;

public interface Tb904RepositoryDsl {

    List<Tb904> getMenuId(Integer type, String acd, String scd, String userId);

    Page<Tb904> findAllByDsl(Pageable pageable);

    List<Tb904> findAllByExcel();
}
