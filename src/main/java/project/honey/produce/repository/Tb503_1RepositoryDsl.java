package project.honey.produce.repository;

import project.honey.produce.dto.Tb503_1Dto;

import java.util.List;

public interface Tb503_1RepositoryDsl {

    List<Tb503_1Dto> findAllByDtos(Integer fk);
}
