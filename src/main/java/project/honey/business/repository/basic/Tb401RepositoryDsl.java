package project.honey.business.repository.basic;

import project.honey.system.dto.CodeDto;

import java.util.List;

public interface Tb401RepositoryDsl {
    List<CodeDto> findAllBySelect(Integer type);
}
