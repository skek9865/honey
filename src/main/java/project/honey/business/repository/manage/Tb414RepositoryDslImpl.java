package project.honey.business.repository.manage;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.business.entity.manage.Tb414;
import project.honey.business.form.analyze.Search040304;
import project.honey.business.form.manage.Search040205;

import javax.persistence.EntityManager;

import java.util.List;

import static project.honey.business.entity.manage.QTb414.tb414;
import static project.honey.business.entity.manage.QTb414_1.tb414_1;

public class Tb414RepositoryDslImpl implements Tb414RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb414RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb414> findAllByDsl(String ymd1, String ymd2, Search040205 search040205, List<Integer> seqList, Pageable pageable) {
        List<Tb414> result = queryFactory.select(tb414)
                .from(tb414)
                .leftJoin(tb414.tb414_1s, tb414_1).fetchJoin()
                .where(
                        shipDtEq(ymd1, ymd2),
                        custEq(search040205.getSCustCd()),
                        goodsSeqIn(search040205.getSGoodsCd(), seqList)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        tb414.seq.asc(),
                        tb414_1.seq.asc()
                )
                .fetch();

        int total = queryFactory.select(tb414)
                .from(tb414)
                .leftJoin(tb414.tb414_1s, tb414_1).fetchJoin()
                .where(
                        shipDtEq(ymd1, ymd2),
                        custEq(search040205.getSCustCd()),
                        goodsSeqIn(search040205.getSGoodsCd(), seqList)
                )
                .fetch()
                .size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb414> findAllByExcel(String ymd1, String ymd2, Search040205 search040205, List<Integer> seqList) {
        List<Tb414> result = queryFactory.select(tb414)
                .from(tb414)
                .leftJoin(tb414.tb414_1s, tb414_1).fetchJoin()
                .where(
                        shipDtEq(ymd1, ymd2),
                        custEq(search040205.getSCustCd()),
                        goodsSeqIn(search040205.getSGoodsCd(), seqList)
                )
                .orderBy(
                        tb414.seq.asc(),
                        tb414_1.seq.asc()
                )
                .fetch();

        return result;
    }

    @Override
    public Page<Tb414> findAllBy040305(String ymd1, String ymd2, Search040304 search040304, Pageable pageable) {
        List<Tb414> result = queryFactory.select(tb414)
                .from(tb414)
                .leftJoin(tb414.tb414_1s, tb414_1).fetchJoin()
                .where(
                        shipDtEq(ymd1, ymd2),
                        whouseEq(search040304.getSWhouseCd()),
                        custEq(search040304.getSCustCd()),
                        goodsEq(search040304.getSGoodsCd())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        tb414.seq.asc(),
                        tb414_1.seq.asc()
                )
                .fetch();

        int total = queryFactory.select(tb414)
                .from(tb414)
                .leftJoin(tb414.tb414_1s, tb414_1).fetchJoin()
                .where(
                        shipDtEq(ymd1, ymd2),
                        whouseEq(search040304.getSWhouseCd()),
                        custEq(search040304.getSCustCd()),
                        goodsEq(search040304.getSGoodsCd())
                )
                .fetch()
                .size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb414> findAllBy040305Excel(String ymd1, String ymd2, Search040304 search040304) {
        List<Tb414> result = queryFactory.select(tb414)
                .from(tb414)
                .leftJoin(tb414.tb414_1s, tb414_1).fetchJoin()
                .where(
                        shipDtEq(ymd1, ymd2),
                        whouseEq(search040304.getSWhouseCd()),
                        custEq(search040304.getSCustCd()),
                        goodsEq(search040304.getSGoodsCd())
                )
                .orderBy(
                        tb414.seq.asc(),
                        tb414_1.seq.asc()
                )
                .fetch();

        return result;
    }

    private BooleanExpression shipDtEq(String ymd1, String ymd2){
        return StringUtils.hasText(ymd1) && StringUtils.hasText(ymd2) ? tb414.shipDt.between(ymd1, ymd2) : null;
    }

    private BooleanExpression custEq(String custCd){
        return StringUtils.hasText(custCd) ? tb414.custCd.eq(custCd) : null;
    }

    private BooleanExpression goodsSeqIn(String goodsCd, List<Integer> seqList){
        return StringUtils.hasText(goodsCd) ? tb414.seq.in(seqList) : null;
    }

    private BooleanExpression goodsEq(String goodsCd){
        return StringUtils.hasText(goodsCd) ? tb414_1.goodsCd.eq(goodsCd) : null;
    }

    private BooleanExpression whouseEq(String whouseCd){
        return StringUtils.hasText(whouseCd) ? tb414.whouseCd.eq(whouseCd) : null;
    }
}
