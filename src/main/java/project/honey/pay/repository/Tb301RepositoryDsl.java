package project.honey.pay.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.pay.dto.Tb301Dto;
import project.honey.pay.entity.Tb301;
import project.honey.system.entity.Tb906;

public interface Tb301RepositoryDsl {

    Page<Tb301Dto> findAllByDsl(Pageable pageable);
}
