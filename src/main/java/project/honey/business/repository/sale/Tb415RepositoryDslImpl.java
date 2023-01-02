package project.honey.business.repository.sale;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.business.dto.search.SearchPopUp410;
import project.honey.business.entity.sale.Tb415;
import project.honey.business.form.sale.Search040401;

import javax.persistence.EntityManager;

import java.util.List;

import static project.honey.business.entity.sale.QTb415.tb415;
import static project.honey.business.entity.sale.QTb415_1.tb415_1;

public class Tb415RepositoryDslImpl implements Tb415RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb415RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb415> findAllByDsl(String ymd1, String ymd2, Search040401 search040401, List<Integer> seqList, Pageable pageable) {
        List<Tb415> result = queryFactory.select(tb415)
                .from(tb415)
                .leftJoin(tb415.tb415_1s, tb415_1).fetchJoin()
                .where(
                        orderDtEq(ymd1, ymd2),
                        empEq(search040401.getSEmpNo()),
                        statusEq(search040401.getSStatus()),
                        custEq(search040401.getSCustCd()),
                        goodsSeqIn(search040401.getSGoodsCd(), seqList)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        tb415.seq.asc(),
                        tb415_1.seq.asc()
                )
                .fetch();

        int total = queryFactory.select(tb415)
                .from(tb415)
                .leftJoin(tb415.tb415_1s, tb415_1).fetchJoin()
                .where(
                        orderDtEq(ymd1, ymd2),
                        empEq(search040401.getSEmpNo()),
                        statusEq(search040401.getSStatus()),
                        custEq(search040401.getSCustCd()),
                        goodsSeqIn(search040401.getSGoodsCd(), seqList)
                )
                .fetch()
                .size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb415> findAllByExcel(String ymd1, String ymd2, Search040401 search040401, List<Integer> seqList) {
        List<Tb415> result = queryFactory.select(tb415)
                .from(tb415)
                .leftJoin(tb415.tb415_1s, tb415_1).fetchJoin()
                .where(
                        orderDtEq(ymd1, ymd2),
                        empEq(search040401.getSEmpNo()),
                        statusEq(search040401.getSStatus()),
                        custEq(search040401.getSCustCd()),
                        goodsSeqIn(search040401.getSGoodsCd(), seqList)
                )
                .orderBy(
                        tb415.seq.asc(),
                        tb415_1.seq.asc()
                )
                .fetch();

        return result;
    }

    @Override
    public List<Tb415> findAllByPopUp(String ymd1, String ymd2, SearchPopUp410 searchPopUp410, List<Integer> seqList) {
        List<Tb415> result = queryFactory.select(tb415)
                .from(tb415)
                .leftJoin(tb415.tb415_1s, tb415_1).fetchJoin()
                .where(
                        orderDtEq(ymd1, ymd2),
                        custEq(searchPopUp410.getSCustCd()),
                        goodsSeqIn(searchPopUp410.getSGoodsCd(), seqList)
                )
                .orderBy(
                        tb415.seq.asc(),
                        tb415_1.seq.asc()
                )
                .fetch();

        return result;
    }

    private BooleanExpression orderDtEq(String ymd1, String ymd2){
        return StringUtils.hasText(ymd1) && StringUtils.hasText(ymd2) ? tb415.orderDt.between(ymd1, ymd2) : null;
    }

    private BooleanExpression empEq(String empNo){
        return StringUtils.hasText(empNo) ? tb415.empNo.eq(empNo) : null;
    }

    private BooleanExpression statusEq(String status){
        return StringUtils.hasText(status) ? tb415.status.eq(status) : null;
    }

    private BooleanExpression custEq(String custCd){
        return StringUtils.hasText(custCd) ? tb415.custCd.eq(custCd) : null;
    }
    private BooleanExpression goodsSeqIn(String goodsCd, List<Integer> seqList){
        return StringUtils.hasText(goodsCd) ? tb415.seq.in(seqList) : null;
    }
}
