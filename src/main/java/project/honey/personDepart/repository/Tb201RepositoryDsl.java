package project.honey.personDepart.repository;

import project.honey.personDepart.entity.Tb201;

import java.util.List;

public interface Tb201RepositoryDsl {

    List<Tb201> findAllByDsl(String empNm, String postCd, String deptCd);
}
