package project.honey.business.repository.basic;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.business.dto.basic.QQuery405Dto;
import project.honey.business.dto.basic.Query405Dto;
import project.honey.business.dto.search.Search405;
import project.honey.business.dto.search.SearchPopUp405;
import project.honey.business.entity.basic.Tb405;
import project.honey.stock.dto.search.Search060201;
import project.honey.system.entity.QTb906;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static project.honey.business.entity.basic.QTb405.*;

public class Tb405RepositoryDslImpl implements Tb405RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb405RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb405> findAllByDsl(Search405 search, Pageable pageable) {

        List<Tb405> result = queryFactory.selectFrom(tb405)
                .where(
                        goodsNmContains(search.getGoodsNm()),
                        goodsCdEq(search.getGoodsCd()),
                        itemGb1Eq(search.getItemGb1()),
                        classSeqEq(search.getClassSeq())
                )
                .orderBy(tb405.seq.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.selectFrom(tb405)
                .where(
                        goodsNmContains(search.getGoodsNm()),
                        itemGb1Eq(search.getItemGb1()),
                        classSeqEq(search.getClassSeq())
                ).fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public Page<Query405Dto> findAllByQuery(Search060201 search, Pageable pageable) {

        List<String> list = search.getClassSeq().stream().filter(StringUtils::hasText).collect(Collectors.toList());

        List<Query405Dto> result = queryFactory.select(
                        new QQuery405Dto(
                                tb405.seq,
                                tb405.standard,
                                tb405.classSeq,
                                tb405.goodsNm,
                                tb405.stockQty
                        ))
                .from(tb405)
                .where(
                        goodsCdEq(search.getGoodsCd()),
                        itemGb1Eq(search.getItemGb()),
                        classSeqIn(list)
                )
                .orderBy(tb405.seq.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.select(tb405)
                .from(tb405)
                .where(
                        goodsCdEq(search.getGoodsCd()),
                        itemGb1Eq(search.getItemGb()),
                        classSeqIn(list)
                )
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Query405Dto> findAllByQueryExcel(Search060201 search) {

        List<String> list = search.getClassSeq().stream().filter(StringUtils::hasText).collect(Collectors.toList());

        return queryFactory.select(
                        new QQuery405Dto(
                                tb405.seq,
                                tb405.standard,
                                tb405.classSeq,
                                tb405.goodsNm,
                                tb405.stockQty
                        ))
                .from(tb405)
                .where(
                        goodsCdEq(search.getGoodsCd()),
                        itemGb1Eq(search.getItemGb()),
                        classSeqIn(list)
                )
                .orderBy(tb405.seq.asc())
                .fetch();
    }

    @Override
    public List<Tb405> findAllByPopUp(SearchPopUp405 search) {
        return queryFactory.selectFrom(tb405)
                .where(
                        goodsNmContains(search.getGoodsNm())
                )
                .orderBy(tb405.seq.asc())
                .fetch();
    }

    @Override
    public List<Tb405> findAllByExcel(Search405 search) {
        return queryFactory.selectFrom(tb405)
                .where(
                        goodsNmContains(search.getGoodsNm()),
                        itemGb1Eq(search.getItemGb1()),
                        classSeqEq(search.getClassSeq())
                )
                .orderBy(tb405.seq.asc())
                .fetch();
    }

    private BooleanExpression goodsNmContains(String goodsNm){
        return StringUtils.hasText(goodsNm) ? tb405.goodsNm.contains(goodsNm) : null;
    }

    private BooleanExpression goodsCdEq(String goodsCd){
        return StringUtils.hasText(goodsCd) ? tb405.goodsCd.eq(goodsCd) : null;
    }

    private BooleanExpression itemGb1Eq(String itemGb1){
        return StringUtils.hasText(itemGb1) ? tb405.itemGb1.eq(itemGb1) : null;
    }

    private BooleanExpression classSeqEq(String classSeq){
        return StringUtils.hasText(classSeq) ? tb405.classSeq.eq(classSeq) : null;
    }

    private BooleanExpression classSeqIn(List<String> classSeq){
        return classSeq.size() != 0 ? tb405.classSeq.in(classSeq) : null;
    }
}
