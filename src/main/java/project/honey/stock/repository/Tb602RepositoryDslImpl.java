package project.honey.stock.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.stock.dto.search.Search060102;
import project.honey.stock.entity.Tb602;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.stock.entity.QTb602.*;

public class Tb602RepositoryDslImpl implements Tb602RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb602RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb602> findAllByDsl(Search060102 search, Pageable pageable) {

        List<Tb602> result = queryFactory.select(tb602)
                .from(tb602)
                .where(
                        whoUseDtBetween(search.getYmd1(), search.getYmd2()),
                        statusEq(search.getStatus())
                )
                .orderBy(tb602.seq.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.select(tb602)
                .from(tb602)
                .where(
                        whoUseDtBetween(search.getYmd1(), search.getYmd2()),
                        statusEq(search.getStatus())
                )
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb602> findAllByExcel(Search060102 search) {
        return queryFactory.select(tb602)
                .from(tb602)
                .where(
                        whoUseDtBetween(search.getYmd1(), search.getYmd2()),
                        statusEq(search.getStatus())
                )
                .orderBy(tb602.seq.asc())
                .fetch();
    }

    private BooleanExpression whoUseDtBetween(String ymd1, String ymd2) {
        return StringUtils.hasText(ymd1) ? tb602.wHouseDt.between(ymd1, ymd2) : null;
    }

    private BooleanExpression statusEq(String status) {
        return StringUtils.hasText(status) ? tb602.status.eq(status) : null;
    }
}
