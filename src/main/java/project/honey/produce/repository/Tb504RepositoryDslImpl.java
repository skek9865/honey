package project.honey.produce.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.produce.dto.search.Search050201;
import project.honey.produce.entity.QTb504_1;
import project.honey.produce.entity.Tb504;

import javax.persistence.EntityManager;

import java.util.List;

import static project.honey.produce.entity.QTb504.*;
import static project.honey.produce.entity.QTb504_1.*;

public class Tb504RepositoryDslImpl implements Tb504RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb504RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb504> findAllByDsl(Search050201 search, Pageable pageable) {

        List<Tb504> result = queryFactory.select(tb504)
                .from(tb504)
                .leftJoin(tb504.tb504_1s).fetchJoin()
                .where(
                        workDtBetween(search.getYmd1(), search.getYmd2()),
                        statusEq(search.getStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.selectFrom(tb504)
                .where(
                        workDtBetween(search.getYmd1(), search.getYmd2()),
                        statusEq(search.getStatus())
                )
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    private BooleanExpression workDtBetween(String ymd1, String ymd2) {
        return StringUtils.hasText(ymd1) ? tb504.workDt.between(ymd1, ymd2) : null;
    }

    private BooleanExpression statusEq(String status) {
        return StringUtils.hasText(status) ? tb504.status.eq(status) : null;
    }
}
