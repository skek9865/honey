package project.honey.produce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.Dto050104;
import project.honey.produce.dto.Tb503_1Dto;
import project.honey.produce.dto.input.Tb503Input;

import java.util.List;

public interface Tb503_1RepositoryDsl {

    List<Tb503_1Dto> findAllByDtos(Integer fk);
}
