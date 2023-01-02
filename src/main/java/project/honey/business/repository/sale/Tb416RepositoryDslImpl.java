package project.honey.business.repository.sale;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.business.entity.manage.Tb412;
import project.honey.business.entity.sale.Tb416;
import project.honey.business.form.analyze.Search040307;
import project.honey.business.form.sale.Search040402;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.business.entity.manage.QTb412.tb412;
import static project.honey.business.entity.manage.QTb412_1.tb412_1;
import static project.honey.business.entity.sale.QTb416.tb416;
import static project.honey.business.entity.sale.QTb416_1.tb416_1;

public class Tb416RepositoryDslImpl implements Tb416RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb416RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb416> findAllByDsl(String ymd1, String ymd2, Search040402 search040402, List<Integer> seqList, List<String> custList, Pageable pageable) {
        List<Tb416> result = queryFactory.select(tb416)
                .from(tb416)
                .leftJoin(tb416.tb416_1s, tb416_1).fetchJoin()
                .where(
                        buyDtEq(ymd1, ymd2),
                        whouseEq(search040402.getSWhouseCd()),
                        saleTypeEq(search040402.getSSaleType()),
                        projectEq(search040402.getSProjectCd()),
                        custGrEq(search040402.getSCustGr(), custList),
                        custEq(search040402.getSCustCd()),
                        goodsSeqIn(search040402.getSGoodsCd(), seqList),
                        statusEq(search040402.getSStatus()),
                        empEq(search040402.getSEmpNo())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        tb416.seq.asc(),
                        tb416_1.seq.asc()
                )
                .fetch();

        int total = queryFactory.select(tb416)
                .from(tb416)
                .leftJoin(tb416.tb416_1s, tb416_1).fetchJoin()
                .where(
                        buyDtEq(ymd1, ymd2),
                        whouseEq(search040402.getSWhouseCd()),
                        saleTypeEq(search040402.getSEmpNo()),
                        projectEq(search040402.getSProjectCd()),
                        custGrEq(search040402.getSCustGr(), custList),
                        custEq(search040402.getSCustCd()),
                        goodsSeqIn(search040402.getSGoodsCd(), seqList),
                        statusEq(search040402.getSStatus()),
                        empEq(search040402.getSEmpNo())
                )
                .fetch()
                .size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb416> findAllByExcel(String ymd1, String ymd2, Search040402 search040402, List<String> custList, List<Integer> seqList) {
        List<Tb416> result = queryFactory.select(tb416)
                .from(tb416)
                .leftJoin(tb416.tb416_1s, tb416_1).fetchJoin()
                .where(
                        buyDtEq(ymd1, ymd2),
                        whouseEq(search040402.getSWhouseCd()),
                        saleTypeEq(search040402.getSSaleType()),
                        projectEq(search040402.getSProjectCd()),
                        custGrEq(search040402.getSCustGr(), custList),
                        custEq(search040402.getSCustCd()),
                        goodsSeqIn(search040402.getSGoodsCd(), seqList),
                        statusEq(search040402.getSStatus()),
                        empEq(search040402.getSEmpNo())
                )
                .orderBy(
                        tb416.seq.asc(),
                        tb416_1.seq.asc()
                )
                .fetch();

        return result;
    }

    @Override
    public List<Tb416> findAllBy040307(String ymd1, String ymd2, Search040307 search040307, List<String> custList, List<String> shipList) {
        List<Tb416> result = queryFactory.select(tb416)
                .from(tb416)
                .leftJoin(tb416.tb416_1s, tb416_1).fetchJoin()
                .where(
                        tb416.custCd.in(shipList),
                        buyDtEq(ymd1, ymd2),
                        empEq(search040307.getSEmpNo()),
                        custGrEq(search040307.getSCustGr(), custList),
                        custEq(search040307.getSCustCd())
                )
                .groupBy(tb416.seq)
                .orderBy(
                        tb416.empNo.asc(),
                        tb416.custCd.asc(),
                        tb416.seq.asc(),
                        tb416_1.seq.asc()
                )
                .fetch();

        int total = queryFactory.select(tb416)
                .from(tb416)
                .leftJoin(tb416.tb416_1s, tb416_1).fetchJoin()
                .where(
                        tb416.custCd.in(shipList),
                        buyDtEq(ymd1, ymd2),
                        empEq(search040307.getSEmpNo()),
                        custGrEq(search040307.getSCustGr(), custList),
                        custEq(search040307.getSCustCd())
                )
                .fetch()
                .size();

        return result;
    }

    private BooleanExpression buyDtEq(String ymd1, String ymd2){
        return StringUtils.hasText(ymd1) && StringUtils.hasText(ymd2) ? tb416.buyDt.between(ymd1, ymd2) : null;
    }

    private BooleanExpression whouseEq(String whouseCd){
        return StringUtils.hasText(whouseCd) ? tb416.whouseCd.eq(whouseCd) : null;
    }

    private BooleanExpression saleTypeEq(String saleType){
        return StringUtils.hasText(saleType) ? tb416.saleType.eq(saleType) : null;
    }

    private BooleanExpression projectEq(String projectCd){
        return StringUtils.hasText(projectCd) ? tb416.projectCd.eq(projectCd) : null;
    }

    private BooleanExpression custGrEq(String custGr, List<String> custList){
        return StringUtils.hasText(custGr) ? tb416.custCd.in(custList) : null;
    }

    private BooleanExpression custEq(String custCd){
        return StringUtils.hasText(custCd) ? tb416.custCd.eq(custCd) : null;
    }

    private BooleanExpression goodsSeqIn(String goodsCd, List<Integer> seqList){
        return StringUtils.hasText(goodsCd) ? tb416.seq.in(seqList) : null;
    }

    private BooleanExpression statusEq(String status){
        return StringUtils.hasText(status) ? tb416.status.eq(status) : null;
    }

    private BooleanExpression empEq(String empNo){
        return StringUtils.hasText(empNo) ? tb416.empNo.eq(empNo) : null;
    }

}
