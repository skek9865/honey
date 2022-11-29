package project.honey.personDepart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.personDepart.entity.Tb203;

import java.util.List;

public interface Tb203RepositoryDsl {

    Page<Tb203> findAllByDsl(String outFNm, String part, Pageable pageable);
    List<Tb203> findAllByExcel(String outFNm, String part);
}
