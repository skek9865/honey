package project.honey.produce.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.produce.dto.*;
import project.honey.produce.entity.QTb506;
import project.honey.produce.entity.QTb506_1;
import project.honey.stock.dto.search.Search060109;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.business.entity.basic.QTb405.tb405;
import static project.honey.produce.entity.QTb505_1.tb505_1;
import static project.honey.produce.entity.QTb506.*;
import static project.honey.produce.entity.QTb506_1.*;
import static project.honey.stock.entity.QTb603_1.tb603_1;

public class Tb506_1RepositoryDslImpl implements Tb506_1RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb506_1RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Query506_1Dto> findAllByQuery(Search060109 search, Pageable pageable) {
        List<Query506_1Dto> result = queryFactory.select(
                        new QQuery506_1Dto(
                                tb506_1.seq,
                                tb506_1.tb506.wHouseIn,
                                tb506_1.goodsCd,
                                tb506_1.qty.sum()
                        ))
                .from(tb506_1)
                .where(
                        wHouseDtBetween(search.getYmd1(), search.getYmd2())
                )
                .groupBy(tb506_1.goodsCd)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(tb506_1.tb506.seq.asc())
                .fetch();

        int total = queryFactory.select(
                        new QQuery506_1Dto(
                                tb506_1.seq,
                                tb506_1.tb506.wHouseIn,
                                tb506_1.goodsCd,
                                tb506_1.qty.sum()
                        ))
                .from(tb506_1)
                .where(
                        wHouseDtBetween(search.getYmd1(), search.getYmd2())
                )
                .groupBy(tb506_1.goodsCd)
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Query506_1Dto> findAllByExcel(Search060109 search) {
        return queryFactory.select(
                        new QQuery506_1Dto(
                                tb506_1.seq,
                                tb506_1.tb506.wHouseIn,
                                tb506_1.goodsCd,
                                tb506_1.qty.sum()
                        ))
                .from(tb506_1)
                .where(
                        wHouseDtBetween(search.getYmd1(), search.getYmd2())
                )
                .groupBy(tb506_1.goodsCd)
                .orderBy(tb506_1.tb506.seq.asc())
                .fetch();
    }

    @Override
    public List<Tb506_1Dto> findAllByDtos(Integer fk) {

        return queryFactory.select(
                        new QTb506_1Dto(
                                tb506_1.seq,
                                tb506_1.tb506.seq,
                                tb506_1.goodsCd,
                                tb405.goodsNm,
                                tb506_1.qty,
                                tb405.wPrice,
                                tb506_1.standard,
                                tb506_1.note
                        ))
                .from(tb506_1)
                .leftJoin(tb405).on(tb506_1.goodsCd.eq(tb405.goodsCd))
                .where(tb506_1.tb506.seq.eq(fk))
                .orderBy(tb506_1.seq.asc())
                .fetch();
    }

    private BooleanExpression wHouseDtBetween(String ymd1, String ymd2) {
        return StringUtils.hasText(ymd1) ? tb506_1.tb506.wHouseDt.between(ymd1, ymd2) : null;
    }
}
