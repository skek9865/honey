package project.honey.stock.repository;

import project.honey.stock.dto.Tb601_1Dto;

import java.util.List;

public interface Tb601_1RepositoryDsl {

    List<Tb601_1Dto> findAllByDtos(Integer fk);
}
