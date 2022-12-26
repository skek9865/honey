package project.honey.business.service.manage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.manage.*;
import project.honey.business.form.manage.*;

import java.util.List;

public interface Service040205 {

    Boolean insert(Tb414Form tb414Form, List<Tb414_1Form> tb414_1Form);

    Page<Tb414MainDto> findAllByDsl(Search040205 search040205, Pageable pageable);

    List<List<String>> findAllByExcel(Search040205 search040205);

    Tb414Dto findById(Integer id);

    List<Tb414_1Dto> findChildByFk(Integer id);

    Long findShipNo();

    Boolean update(Tb414Form tb414Form, List<Tb414_1Form> tb414_1Form);

    Boolean delete(Integer id);
}
