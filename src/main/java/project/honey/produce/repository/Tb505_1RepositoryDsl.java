package project.honey.produce.repository;

import project.honey.produce.dto.Tb504_1Dto;
import project.honey.produce.dto.Tb505_1Dto;

import java.util.List;

public interface Tb505_1RepositoryDsl {

    List<Tb505_1Dto> findAllByDtos(Integer fk);
}
