package project.honey.pay.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.pay.dto.Tb301Dto;
public interface Service030101 {

    Integer insert(Tb301Dto dto);

    Integer update(Tb301Dto dto);

    Page<Tb301Dto> findAll(Pageable pageable);

    Tb301Dto findById(Integer seq);

    void delete(Integer seq);
}
