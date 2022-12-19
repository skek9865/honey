package project.honey.business.repository.basic;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.honey.system.dto.CodeDto;
import project.honey.system.dto.QCodeDto;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.business.entity.basic.QTb409.tb409;

public class Tb409RepositoryDslImpl implements Tb409RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb409RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CodeDto> findAllBySelect() {
        List<CodeDto> result = queryFactory
                .select(
                        new QCodeDto(
                                tb409.excgNm,
                                tb409.excgCd
                        ))
                .from(tb409)
                .fetch();

        return result;
    }
}
