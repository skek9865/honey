package project.honey.stock.repository;

import project.honey.stock.dto.Tb602_1Dto;

import java.util.List;

public interface Tb602_1RepositoryDsl {

    List<Tb602_1Dto> findAllByDtos(Integer fk);
}
