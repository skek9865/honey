package project.honey.business.repository.manage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.entity.manage.Tb414;
import project.honey.business.form.manage.Search040205;

import java.util.List;

public interface Tb414RepositoryDsl {
    Page<Tb414> findAllByDsl(String ymd1, String ymd2, Search040205 search040205, List<Integer> seqList, Pageable pageable);
    List<Tb414> findAllByExcel(String ymd1, String ymd2, Search040205 search040205, List<Integer> seqList);
}
