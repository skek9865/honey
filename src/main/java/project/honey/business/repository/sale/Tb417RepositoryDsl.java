package project.honey.business.repository.sale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.entity.sale.Tb417;
import project.honey.business.form.sale.Search040404;

import java.util.List;

public interface Tb417RepositoryDsl {

    Page<Tb417> findAllBy040404(String ymd1, String ymd2, Search040404 search040404, Pageable pageable);
    List<Tb417> findAllBy040404Excel(String ymd1, String ymd2, Search040404 search040404);
}
