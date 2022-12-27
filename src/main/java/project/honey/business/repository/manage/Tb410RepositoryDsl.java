package project.honey.business.repository.manage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.search.SearchPopUp410;
import project.honey.business.entity.manage.Tb410;
import project.honey.business.entity.manage.Tb412;
import project.honey.business.form.analyze.Search040301;
import project.honey.business.form.analyze.Search040302;
import project.honey.business.form.manage.Search040201;

import java.util.List;

public interface Tb410RepositoryDsl {

    Page<Tb410> findAllByDsl(String ymd1, String ymd2, Search040201 search040201,List<Integer> seqList, Pageable pageable);
    List<Tb410> findAllByExcel(String ymd1, String ymd2, Search040201 search040201, List<Integer> seqList);
    List<Tb410> findAllByPopUp(String ymd1, String ymd2, SearchPopUp410 searchPopUp410, List<Integer> seqList);
    Page<Tb410> findAllBy040302(String ymd1, String ymd2, Search040302 search040302, Pageable pageable);
    List<Tb410> findAllBy040302Excel(String ymd1, String ymd2, Search040302 search040302);
}
