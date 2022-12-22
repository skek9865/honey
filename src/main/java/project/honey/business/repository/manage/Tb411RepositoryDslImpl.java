package project.honey.business.repository.manage;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.business.dto.search.SearchPopUp410;
import project.honey.business.entity.manage.Tb410;
import project.honey.business.entity.manage.Tb411;
import project.honey.business.form.manage.Search040201;

import javax.persistence.EntityManager;

import java.util.List;

import static project.honey.business.entity.manage.QTb410.tb410;
import static project.honey.business.entity.manage.QTb410_1.tb410_1;
import static project.honey.business.entity.manage.QTb411.tb411;
import static project.honey.business.entity.manage.QTb411_1.tb411_1;

public class Tb411RepositoryDslImpl implements Tb411RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb411RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb411> findAllByDsl(String ymd1, String ymd2, Search040201 search040201, List<Integer> seqList, Pageable pageable) {
        List<Tb411> result = queryFactory.select(tb411)
                .from(tb411)
                .leftJoin(tb411.tb411_1s, tb411_1).fetchJoin()
                .where(
                        orderDtEq(ymd1, ymd2),
                        empEq(search040201.getSEmpNo()),
                        statusEq(search040201.getSStatus()),
                        custEq(search040201.getSCustCd()),
                        goodsSeqIn(search040201.getSGoodsCd(), seqList)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        tb411.seq.asc(),
                        tb411_1.seq.asc()
                )
                .fetch();

        int total = queryFactory.select(tb411)
                .from(tb411)
                .leftJoin(tb411.tb411_1s, tb411_1).fetchJoin()
                .where(
                        orderDtEq(search040201.getSYmd1(), search040201.getSYmd2()),
                        empEq(search040201.getSEmpNo()),
                        statusEq(search040201.getSStatus()),
                        custEq(search040201.getSCustCd()),
                        goodsSeqIn(search040201.getSGoodsCd(), seqList)
                )
                .fetch()
                .size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb411> findAllByExcel(String ymd1, String ymd2, Search040201 search040201, List<Integer> seqList) {
        List<Tb411> result = queryFactory.select(tb411)
                .from(tb411)
                .leftJoin(tb411.tb411_1s, tb411_1).fetchJoin()
                .where(
                        orderDtEq(ymd1, ymd2),
                        empEq(search040201.getSEmpNo()),
                        statusEq(search040201.getSStatus()),
                        custEq(search040201.getSCustCd()),
                        goodsSeqIn(search040201.getSGoodsCd(), seqList)
                )
                .orderBy(
                        tb411.seq.asc(),
                        tb411_1.seq.asc()
                )
                .fetch();

        return result;
    }

    @Override
    public List<Tb411> findAllByPopUp(String ymd1, String ymd2, SearchPopUp410 searchPopUp410, List<Integer> seqList) {
        List<Tb411> result = queryFactory.select(tb411)
                .from(tb411)
                .leftJoin(tb411.tb411_1s, tb411_1).fetchJoin()
                .where(
                        orderDtEq(ymd1, ymd2),
                        custEq(searchPopUp410.getSCustCd()),
                        goodsSeqIn(searchPopUp410.getSGoodsCd(), seqList)
                )
                .orderBy(
                        tb411.seq.asc(),
                        tb411_1.seq.asc()
                )
                .fetch();

        return result;
    }

    private BooleanExpression orderDtEq(String ymd1, String ymd2){
        return StringUtils.hasText(ymd1) && StringUtils.hasText(ymd2) ? tb411.orderDt.between(ymd1, ymd2) : null;
    }

    private BooleanExpression empEq(String empNo){
        return StringUtils.hasText(empNo) ? tb411.empNo.eq(empNo) : null;
    }

    private BooleanExpression statusEq(String status){
        return StringUtils.hasText(status) ? tb411.status.eq(status) : null;
    }

    private BooleanExpression custEq(String custCd){
        return StringUtils.hasText(custCd) ? tb411.custCd.eq(custCd) : null;
    }
    private BooleanExpression goodsSeqIn(String goodsCd, List<Integer> seqList){
        return StringUtils.hasText(goodsCd) ? tb411.seq.in(seqList) : null;
    }
}
