package project.honey.business.repository.sale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.entity.sale.Tb416;
import project.honey.business.form.analyze.Search040307;
import project.honey.business.form.sale.Search040402;

import java.util.List;

public interface Tb416RepositoryDsl {

    Page<Tb416> findAllByDsl(String ymd1, String ymd2, Search040402 search040402, List<Integer> seqList, List<String> custList, Pageable pageable);
    List<Tb416> findAllByExcel(String ymd1, String ymd2, Search040402 search040402, List<String> custList, List<Integer> seqList);
    List<Tb416> findAllBy040307(String ymd1, String ymd2, Search040307 search040307, List<String> custList, List<String> shipList);
}
