package project.honey.business.repository.manage;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.business.dto.search.SearchPopUp410;
import project.honey.business.entity.manage.Tb413;
import project.honey.business.form.manage.Search040204;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.business.entity.manage.QTb413.tb413;
import static project.honey.business.entity.manage.QTb413_1.tb413_1;

public class Tb413RepositoryDslImpl implements Tb413RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb413RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb413> findAllByDsl(String ymd1, String ymd2, Search040204 search040204, List<Integer> seqList, Pageable pageable) {
        List<Tb413> result = queryFactory.select(tb413)
                .from(tb413)
                .leftJoin(tb413.tb413_1s, tb413_1).fetchJoin()
                .where(
                        shipDtEq(ymd1, ymd2),
                        whouseEq(search040204.getSWhouseCd()),
                        statusEq(search040204.getSStatus()),
                        custEq(search040204.getSCustCd()),
                        goodsSeqIn(search040204.getSGoodsCd(), seqList)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        tb413.seq.asc(),
                        tb413_1.seq.asc()
                )
                .fetch();

        int total = queryFactory.select(tb413)
                .from(tb413)
                .leftJoin(tb413.tb413_1s, tb413_1).fetchJoin()
                .where(
                        shipDtEq(ymd1, ymd2),
                        whouseEq(search040204.getSWhouseCd()),
                        statusEq(search040204.getSStatus()),
                        custEq(search040204.getSCustCd()),
                        goodsSeqIn(search040204.getSGoodsCd(), seqList)
                )
                .fetch()
                .size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb413> findAllByExcel(String ymd1, String ymd2, Search040204 search040204, List<Integer> seqList) {
        List<Tb413> result = queryFactory.select(tb413)
                .from(tb413)
                .leftJoin(tb413.tb413_1s, tb413_1).fetchJoin()
                .where(
                        shipDtEq(ymd1, ymd2),
                        whouseEq(search040204.getSWhouseCd()),
                        statusEq(search040204.getSStatus()),
                        custEq(search040204.getSCustCd()),
                        goodsSeqIn(search040204.getSGoodsCd(), seqList)
                )
                .orderBy(
                        tb413.seq.asc(),
                        tb413_1.seq.asc()
                )
                .fetch();

        return result;
    }

    @Override
    public List<Tb413> findAllByPopUp(String ymd1, String ymd2, SearchPopUp410 searchPopUp410, List<Integer> seqList) {
        List<Tb413> result = queryFactory.select(tb413)
                .from(tb413)
                .leftJoin(tb413.tb413_1s, tb413_1).fetchJoin()
                .where(
                        shipDtEq(ymd1, ymd2),
                        custEq(searchPopUp410.getSCustCd()),
                        goodsSeqIn(searchPopUp410.getSGoodsCd(), seqList)
                )
                .orderBy(
                        tb413.seq.asc(),
                        tb413_1.seq.asc()
                )
                .fetch();

        return result;
    }

    private BooleanExpression shipDtEq(String ymd1, String ymd2){
        return StringUtils.hasText(ymd1) && StringUtils.hasText(ymd2) ? tb413.shipDt.between(ymd1, ymd2) : null;
    }

    private BooleanExpression whouseEq(String whouseCd){
        return StringUtils.hasText(whouseCd) ? tb413.whouseCd.eq(whouseCd) : null;
    }

    private BooleanExpression statusEq(String status){
        return StringUtils.hasText(status) ? tb413.status.eq(status) : null;
    }

    private BooleanExpression custEq(String custCd){
        return StringUtils.hasText(custCd) ? tb413.custCd.eq(custCd) : null;
    }

    private BooleanExpression goodsSeqIn(String goodsCd, List<Integer> seqList){
        return StringUtils.hasText(goodsCd) ? tb413.seq.in(seqList) : null;
    }
}
