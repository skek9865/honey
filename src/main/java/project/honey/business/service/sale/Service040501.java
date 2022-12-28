package project.honey.business.service.sale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.sale.Tb417Dto;
import project.honey.business.form.sale.Tb417Form;
import java.util.List;

public interface Service040501 {

    Boolean insert(Tb417Form form);

    Page<Tb417Dto> findAll(Pageable pageable);

    List<List<String>> findAllByExcel();

    Tb417Dto findById(Integer id);

    Boolean update(Tb417Form form);

    Boolean delete(Integer id);
}
