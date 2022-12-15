package project.honey.produce.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.produce.dto.search.Search050301;
import project.honey.produce.dto.search.Search050302;
import project.honey.produce.entity.QTb506;
import project.honey.produce.entity.Tb505;
import project.honey.produce.entity.Tb506;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.produce.entity.QTb505.tb505;
import static project.honey.produce.entity.QTb506.*;

public class Tb506RepositoryDslImpl implements Tb506RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb506RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<Tb506> findAllByDsl(Search050302 search, Pageable pageable) {

        List<Tb506> result = queryFactory.select(tb506)
                .from(tb506)
                .leftJoin(tb506.tb506_1s).fetchJoin()
                .where(
                        whoUseDtBetween(search.getYmd1(), search.getYmd2()),
                        statusEq(search.getStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.selectFrom(tb506)
                .where(
                        whoUseDtBetween(search.getYmd1(), search.getYmd2()),
                        statusEq(search.getStatus())
                )
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb506> findAllByExcel(Search050302 search) {
        return queryFactory.select(tb506)
                .distinct()
                .from(tb506)
                .leftJoin(tb506.tb506_1s).fetchJoin()
                .where(
                        whoUseDtBetween(search.getYmd1(), search.getYmd2()),
                        statusEq(search.getStatus())
                ).fetch();
    }

    private BooleanExpression whoUseDtBetween(String ymd1, String ymd2) {
        return StringUtils.hasText(ymd1) ? tb506.wHouseDt.between(ymd1, ymd2) : null;
    }

    private BooleanExpression statusEq(String status) {
        return StringUtils.hasText(status) ? tb506.status.eq(status) : null;
    }
}
