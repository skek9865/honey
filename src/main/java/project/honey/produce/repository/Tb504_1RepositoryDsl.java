package project.honey.produce.repository;

import project.honey.produce.dto.Tb504_1Dto;

import java.util.List;

public interface Tb504_1RepositoryDsl {

    List<Tb504_1Dto> findAllByDtos(Integer fk);
}
