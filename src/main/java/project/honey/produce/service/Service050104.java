package project.honey.produce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.Dto050104;

import java.util.List;

public interface Service050104 {

    Page<Dto050104> findAll(String goodsCd, Pageable pageable);

    List<List<String>> findAllByExcel(String goodsCd);
}
