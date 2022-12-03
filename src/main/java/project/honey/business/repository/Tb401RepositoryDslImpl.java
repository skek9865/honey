package project.honey.business.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import project.honey.system.dto.CodeDto;
import project.honey.system.dto.QCodeDto;

import javax.persistence.EntityManager;

import java.util.List;

import static project.honey.business.entity.QTb401.tb401;

public class Tb401RepositoryDslImpl implements Tb401RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb401RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CodeDto> findAllBySelect(Integer type) {

        List<CodeDto> result = queryFactory
                .select(
                        new QCodeDto(
                                tb401.classNm,
                                tb401.classCd
                        ))
                .from(tb401)
                .where(
                        filterClassSeq(type)
                )
                .fetch();

        return result;
    }

    private BooleanExpression filterClassSeq(Integer type){
        switch (type){
            case 1:
                return tb401.classSeq.eq("00001");
            case 2:
                return tb401.classSeq.eq("00002");
        }
        return null;
    }
}
