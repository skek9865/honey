package project.honey.personDepart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.personDepart.entity.Tb204;

import java.util.List;

public interface Tb204RepositoryDsl {

    Page<Tb204> findAllByDsl(String outFNm, String part, Pageable pageable);
    List<Tb204> findAllByExcel(String outFNm, String part);
}
