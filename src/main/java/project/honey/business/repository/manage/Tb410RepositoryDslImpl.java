package project.honey.business.repository.manage;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.business.dto.search.SearchPopUp410;
import project.honey.business.entity.manage.Tb410;
import project.honey.business.form.manage.Search040201;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.business.entity.manage.QTb410.tb410;
import static project.honey.business.entity.manage.QTb410_1.tb410_1;

public class Tb410RepositoryDslImpl implements Tb410RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb410RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb410> findAllByDsl(String ymd1, String ymd2, Search040201 search040201, List<Integer> seqList, Pageable pageable) {
        List<Tb410> result = queryFactory.select(tb410)
                .from(tb410)
                .leftJoin(tb410.tb410_1s, tb410_1).fetchJoin()
                .where(
                        estimDtEq(ymd1, ymd2),
                        empEq(search040201.getSEmpNo()),
                        statusEq(search040201.getSStatus()),
                        custEq(search040201.getSCustCd()),
                        goodsSeqIn(seqList)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        tb410.seq.asc(),
                        tb410_1.seq.asc()
                )
                .fetch();

        int total = queryFactory.select(tb410)
                .from(tb410)
                .leftJoin(tb410.tb410_1s, tb410_1).fetchJoin()
                .where(
                        estimDtEq(search040201.getSYmd1(), search040201.getSYmd2()),
                        empEq(search040201.getSEmpNo()),
                        statusEq(search040201.getSStatus()),
                        custEq(search040201.getSCustCd()),
                        goodsSeqIn(seqList)
                )
                .fetch()
                .size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb410> findAllByExcel(String ymd1, String ymd2, Search040201 search040201, List<Integer> seqList) {
        List<Tb410> result = queryFactory.select(tb410)
                .from(tb410)
                .leftJoin(tb410.tb410_1s, tb410_1).fetchJoin()
                .where(
                        estimDtEq(ymd1, ymd2),
                        empEq(search040201.getSEmpNo()),
                        statusEq(search040201.getSStatus()),
                        custEq(search040201.getSCustCd()),
                        goodsSeqIn(seqList)
                )
                .orderBy(
                        tb410.seq.asc(),
                        tb410_1.seq.asc()
                )
                .fetch();

        return result;
    }

    @Override
    public List<Tb410> findAllByPopUp(String ymd1, String ymd2, SearchPopUp410 searchPopUp410, List<Integer> seqList) {
        List<Tb410> result = queryFactory.select(tb410)
                .from(tb410)
                .leftJoin(tb410.tb410_1s, tb410_1).fetchJoin()
                .where(
                        estimDtEq(ymd1, ymd2),
                        custEq(searchPopUp410.getSCustCd()),
                        goodsSeqIn(seqList)
                )
                .orderBy(
                        tb410.seq.asc(),
                        tb410_1.seq.asc()
                )
                .fetch();

        return result;
    }

    private BooleanExpression estimDtEq(String ymd1, String ymd2){
        return StringUtils.hasText(ymd1) && StringUtils.hasText(ymd2) ? tb410.estimDt.between(ymd1, ymd2) : null;
    }

    private BooleanExpression empEq(String empNo){
        return StringUtils.hasText(empNo) ? tb410.empNo.eq(empNo) : null;
    }

    private BooleanExpression statusEq(String status){
        return StringUtils.hasText(status) ? tb410.status.eq(status) : null;
    }

    private BooleanExpression custEq(String custCd){
        return StringUtils.hasText(custCd) ? tb410.custCd.eq(custCd) : null;
    }
    private BooleanExpression goodsSeqIn(List<Integer> seqList){
        return seqList.size() > 0 ? tb410.seq.in(seqList) : null;
    }
}
