package project.honey.business.repository.manage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.search.SearchPopUp410;
import project.honey.business.entity.manage.Tb412;
import project.honey.business.form.analyze.Search040301;
import project.honey.business.form.manage.Search040203;

import java.util.List;

public interface Tb412RepositoryDsl {
    Page<Tb412> findAllByDsl(String ymd1, String ymd2, Search040203 search040203, List<Integer> seqList, Pageable pageable);
    List<Tb412> findAllByExcel(String ymd1, String ymd2, Search040203 search040203, List<Integer> seqList);
    List<Tb412> findAllByPopUp(String ymd1, String ymd2, SearchPopUp410 searchPopUp410, List<Integer> seqList);
    Page<Tb412> findAllBy040301(String ymd1, String ymd2, Search040301 search040301, List<Integer> seqList, List<String> custList, Pageable pageable);
    List<Tb412> findAllBy040301Excel(String ymd1, String ymd2, Search040301 search040301, List<Integer> seqList, List<String> custList);
}
