package project.honey.pay.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.pay.dto.Tb302ResultDto;

public interface Tb302RepositoryDsl {

    Page<Tb302ResultDto> findAllResultByDsl(Pageable pageable);
}
