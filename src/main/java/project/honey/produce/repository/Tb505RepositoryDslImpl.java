package project.honey.produce.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.produce.dto.search.Search050301;
import project.honey.produce.entity.Tb505;

import javax.persistence.EntityManager;

import java.util.List;

import static project.honey.produce.entity.QTb505.*;

public class Tb505RepositoryDslImpl implements Tb505RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb505RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<Tb505> findAllByDsl(Search050301 search, Pageable pageable) {

        List<Tb505> result = queryFactory.select(tb505)
                .from(tb505)
//                .leftJoin(tb505.tb505_1s).fetchJoin()
                .where(
                        whoUseDtBetween(search.getYmd1(), search.getYmd2()),
                        statusEq(search.getStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.selectFrom(tb505)
                .where(
                        whoUseDtBetween(search.getYmd1(), search.getYmd2()),
                        statusEq(search.getStatus())
                )
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb505> findAllByExcel(Search050301 search) {
        return queryFactory.select(tb505)
                .distinct()
                .from(tb505)
//                .leftJoin(tb505.tb505_1s).fetchJoin()
                .where(
                        whoUseDtBetween(search.getYmd1(), search.getYmd2()),
                        statusEq(search.getStatus())
                ).fetch();
    }

    private BooleanExpression whoUseDtBetween(String ymd1, String ymd2) {
        return StringUtils.hasText(ymd1) ? tb505.wHouseDt.between(ymd1, ymd2) : null;
    }

    private BooleanExpression statusEq(String status) {
        return StringUtils.hasText(status) ? tb505.status.eq(status) : null;
    }
}
