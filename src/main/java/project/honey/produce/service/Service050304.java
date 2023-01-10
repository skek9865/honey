package project.honey.produce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.Dto050304;

import java.util.List;

public interface Service050304 {

    Page<Dto050304> findAll(Pageable pageable);

    List<List<String>> findAllByExcel();
}
