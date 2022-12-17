package project.honey.stock.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.stock.dto.search.Search060103;
import project.honey.stock.entity.QTb603;
import project.honey.stock.entity.Tb603;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.stock.entity.QTb603.*;

public class Tb603RepositoryDslImpl implements Tb603RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb603RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb603> findAllByDsl(Search060103 search, Pageable pageable) {

        List<Tb603> result = queryFactory.select(tb603)
                .from(tb603)
                .where(
                        whoUseDtBetween(search.getYmd1(), search.getYmd2()),
                        statusEq(search.getStatus())
                )
                .orderBy(tb603.seq.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.select(tb603)
                .from(tb603)
                .where(
                        whoUseDtBetween(search.getYmd1(), search.getYmd2()),
                        statusEq(search.getStatus())
                )
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb603> findAllByExcel(Search060103 search) {
        return queryFactory.select(tb603)
                .from(tb603)
                .where(
                        whoUseDtBetween(search.getYmd1(), search.getYmd2()),
                        statusEq(search.getStatus())
                )
                .orderBy(tb603.seq.asc())
                .fetch();
    }

    private BooleanExpression whoUseDtBetween(String ymd1, String ymd2) {
        return StringUtils.hasText(ymd1) ? tb603.wHouseDt.between(ymd1, ymd2) : null;
    }

    private BooleanExpression statusEq(String status) {
        return StringUtils.hasText(status) ? tb603.status.eq(status) : null;
    }
}
