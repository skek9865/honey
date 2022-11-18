package project.honey.system.repository;

import project.honey.system.entity.Tb904;

import java.util.List;

public interface Tb904RepositoryDsl {

    List<Tb904> getMenuId(Integer type, String acd, String scd, String userId);
    List<Tb904> findMenuNm(String fstId);
}
