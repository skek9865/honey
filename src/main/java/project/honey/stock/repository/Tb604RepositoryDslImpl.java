package project.honey.stock.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.business.entity.basic.QTb405;
import project.honey.stock.dto.query.QQuery604Dto;
import project.honey.stock.dto.query.Query604Dto;
import project.honey.stock.dto.search.Search060110;
import project.honey.stock.dto.search.Search060111;
import project.honey.stock.entity.Tb604;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.business.entity.basic.QTb405.*;
import static project.honey.stock.entity.QTb604.*;

public class Tb604RepositoryDslImpl implements Tb604RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb604RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<Tb604> findAllByDsl(Search060110 search, Pageable pageable) {

        List<Tb604> result = queryFactory.selectFrom(tb604)
                .where(
                        wHouseDtBetween(search.getYmd1(), search.getYmd2())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(tb604.seq.asc())
                .fetch();

        int total = queryFactory.selectFrom(tb604)
                .where(
                        wHouseDtBetween(search.getYmd1(), search.getYmd2())
                )
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb604> findAllByExcel(Search060110 search) {
        return queryFactory.selectFrom(tb604)
                .where(
                        wHouseDtBetween(search.getYmd1(), search.getYmd2())
                )
                .orderBy(tb604.seq.asc())
                .fetch();
    }

    @Override
    public Page<Query604Dto> findAllByQuery(Search060111 search, Pageable pageable) {

        List<Query604Dto> result = queryFactory.select(
                        new QQuery604Dto(
                                tb604.seq,
                                tb604.wHouseDt,
                                tb604.wHouseNo,
                                tb405.goodsNm,
                                tb604.wHouseOut,
                                tb604.stQty,
                                tb604.reQty,
                                tb604.adQty,
                                tb405.wPrice,
                                tb604.note
                        ))
                .from(tb604)
                .leftJoin(tb405).on(tb604.goodsCd.eq(tb405.goodsCd))
                .where(
                        wHouseDtBetween(search.getYmd1(), search.getYmd2())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(tb604.seq.asc())
                .fetch();

        int total = queryFactory.selectFrom(tb604)
                .where(
                        wHouseDtBetween(search.getYmd1(), search.getYmd2())
                )
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Query604Dto> findAllByQueryExcel(Search060111 search) {
        return queryFactory.select(
                        new QQuery604Dto(
                                tb604.seq,
                                tb604.wHouseDt,
                                tb604.wHouseNo,
                                tb405.goodsNm,
                                tb604.wHouseOut,
                                tb604.stQty,
                                tb604.reQty,
                                tb604.adQty,
                                tb405.wPrice,
                                tb604.note
                        ))
                .from(tb604)
                .leftJoin(tb405).on(tb604.goodsCd.eq(tb405.goodsCd))
                .where(
                        wHouseDtBetween(search.getYmd1(), search.getYmd2())
                )
                .orderBy(tb604.seq.asc())
                .fetch();
    }

    private BooleanExpression wHouseDtBetween(String ymd1, String ymd2) {
        return StringUtils.hasText(ymd1) ? tb604.wHouseDt.between(ymd1, ymd2) : null;
    }
}
