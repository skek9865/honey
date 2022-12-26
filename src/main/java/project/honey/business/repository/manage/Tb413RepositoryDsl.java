package project.honey.business.repository.manage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.search.SearchPopUp410;
import project.honey.business.entity.manage.Tb413;
import project.honey.business.form.manage.Search040204;

import java.util.List;

public interface Tb413RepositoryDsl {
    Page<Tb413> findAllByDsl(String ymd1, String ymd2, Search040204 search040204, List<Integer> seqList, Pageable pageable);
    List<Tb413> findAllByExcel(String ymd1, String ymd2, Search040204 search040204, List<Integer> seqList);
    List<Tb413> findAllByPopUp(String ymd1, String ymd2, SearchPopUp410 searchPopUp410, List<Integer> seqList);
}
