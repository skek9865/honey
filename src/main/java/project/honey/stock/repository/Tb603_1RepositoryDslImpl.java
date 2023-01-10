package project.honey.stock.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.produce.entity.Tb503_1;
import project.honey.stock.dto.search.Search060102;
import project.honey.stock.dto.search.Search060107;
import project.honey.stock.entity.QTb603_1;
import project.honey.stock.entity.Tb602;
import project.honey.stock.entity.Tb603_1;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.produce.entity.QTb503_1.tb503_1;
import static project.honey.stock.entity.QTb601.tb601;
import static project.honey.stock.entity.QTb602.tb602;
import static project.honey.stock.entity.QTb603_1.*;

public class Tb603_1RepositoryDslImpl implements Tb603_1RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb603_1RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<Tb603_1> findAllByDsl(Search060107 search, Pageable pageable) {
        List<Tb603_1> result = queryFactory.select(tb603_1)
                .from(tb603_1)
                .leftJoin(tb603_1.tb603).fetchJoin()
                .offset(pageable.getOffset())
                .where(
                        whoUseDtBetween(search.getYmd1(), search.getYmd2())
                )
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.select(tb603_1)
                .from(tb603_1)
                .where(
                        whoUseDtBetween(search.getYmd1(), search.getYmd2())
                )
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb603_1> findAllByExcel(Search060107 search) {
        return queryFactory.select(tb603_1)
                .from(tb603_1)
                .leftJoin(tb603_1.tb603).fetchJoin()
                .where(
                        whoUseDtBetween(search.getYmd1(), search.getYmd2())
                )
                .fetch();
    }

    private BooleanExpression whoUseDtBetween(String ymd1, String ymd2) {
        return StringUtils.hasText(ymd1) ? tb603_1.tb603.wHouseDt.between(ymd1, ymd2) : null;
    }
}
