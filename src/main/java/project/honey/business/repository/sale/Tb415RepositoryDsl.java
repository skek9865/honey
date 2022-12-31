package project.honey.business.repository.sale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.entity.sale.Tb415;
import project.honey.business.form.sale.Search040401;

import java.util.List;

public interface Tb415RepositoryDsl {
    Page<Tb415> findAllByDsl(String ymd1, String ymd2, Search040401 search040401, List<Integer> seqList, Pageable pageable);
    List<Tb415> findAllByExcel(String ymd1, String ymd2, Search040401 search040401, List<Integer> seqList);
}
