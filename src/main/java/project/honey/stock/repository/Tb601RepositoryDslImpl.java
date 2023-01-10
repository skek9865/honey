package project.honey.stock.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.stock.dto.search.Search060101;
import project.honey.stock.entity.Tb601;

import javax.persistence.EntityManager;

import java.util.List;

import static project.honey.stock.entity.QTb601.*;

public class Tb601RepositoryDslImpl implements Tb601RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb601RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb601> findAllByDsl(Search060101 search, Pageable pageable) {

        List<Tb601> result = queryFactory.select(tb601)
                .from(tb601)
                .where(
                        whoUseDtBetween(search.getYmd1(), search.getYmd2()),
                        statusEq(search.getStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.select(tb601)
                .from(tb601)
                .where(
                        whoUseDtBetween(search.getYmd1(), search.getYmd2()),
                        statusEq(search.getStatus())
                )
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb601> findAllByExcel(Search060101 search) {
        return queryFactory.select(tb601)
                .from(tb601)
                .where(
                        whoUseDtBetween(search.getYmd1(), search.getYmd2()),
                        statusEq(search.getStatus())
                )
                .fetch();
    }

    private BooleanExpression whoUseDtBetween(String ymd1, String ymd2) {
        return StringUtils.hasText(ymd1) ? tb601.wHouseDt.between(ymd1, ymd2) : null;
    }

    private BooleanExpression statusEq(String status) {
        return StringUtils.hasText(status) ? tb601.status.eq(status) : null;
    }
}
