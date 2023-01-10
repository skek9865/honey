package project.honey.personDepart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.personDepart.entity.Tb201;
import project.honey.system.dto.CodeDto;

import java.util.List;


public interface Tb201RepositoryDsl {

    Page<Tb201> findAllByDsl(String empNm, String postCd, String deptCd, Pageable pageable);
    Page<Tb201> findAllByLeave(String empNm, String postCd, String deptCd, Pageable pageable);
    List<Tb201> findAllByExcel(String empNm, String postCd, String deptCd);
    List<CodeDto> findAllBySelect();
}
