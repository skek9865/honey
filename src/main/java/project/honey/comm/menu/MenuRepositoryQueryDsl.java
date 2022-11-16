package project.honey.comm.menu;

import java.util.List;

public interface MenuRepositoryQueryDsl {

    List<Tb904> getMenuId(Integer type, String acd, String scd, String userId);
}
