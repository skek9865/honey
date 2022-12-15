package project.honey.produce.repository;

import project.honey.produce.dto.Tb505_1Dto;
import project.honey.produce.dto.Tb506_1Dto;

import java.util.List;

public interface Tb506_1RepositoryDsl {

    List<Tb506_1Dto> findAllByDtos(Integer fk);
}
