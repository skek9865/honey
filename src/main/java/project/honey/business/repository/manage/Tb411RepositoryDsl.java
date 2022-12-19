package project.honey.business.repository.manage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.entity.manage.Tb411;
import project.honey.business.form.manage.Search040201;

import java.util.List;

public interface Tb411RepositoryDsl {
    Page<Tb411> findAllByDsl(String ymd1, String ymd2, Search040201 search040201, List<Integer> seqList, Pageable pageable);
    List<Tb411> findAllByExcel(String ymd1, String ymd2, Search040201 search040201, List<Integer> seqList);
}
