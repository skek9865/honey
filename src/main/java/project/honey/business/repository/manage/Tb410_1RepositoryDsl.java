package project.honey.business.repository.manage;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.entity.manage.Tb410_1;
import project.honey.business.form.manage.Search040201;

import java.util.List;

public interface Tb410_1RepositoryDsl {

    Page<Tb410_1> findAllByDsl(String ymd1, String ymd2, Search040201 search040201, List<Integer> seqList, Pageable pageable);
    List<Tb410_1> findAllByExcel(String ymd1, String ymd2, Search040201 search040201, List<Integer> seqList);
}
