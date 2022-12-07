package project.honey.business.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.honey.system.dto.CodeDto;
import project.honey.system.dto.QCodeDto;

import javax.persistence.EntityManager;

import java.util.List;

import static project.honey.business.entity.QTb406.tb406;

public class Tb406RepositoryDslImpl implements Tb406RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb406RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CodeDto> findAllBySelect() {

        List<CodeDto> result = queryFactory
                .select(
                        new QCodeDto(
                                tb406.specialNm,
                                tb406.specialCd
                        ))
                .from(tb406)
                .fetch();

        return result;
    }
}
