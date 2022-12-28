package project.honey.business.repository.manage;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.business.dto.search.SearchPopUp410;
import project.honey.business.entity.manage.Tb412;
import project.honey.business.form.analyze.Search040301;
import project.honey.business.form.analyze.Search040306;
import project.honey.business.form.manage.Search040203;

import javax.persistence.EntityManager;

import java.util.List;

import static project.honey.business.entity.basic.QTb402.tb402;
import static project.honey.business.entity.manage.QTb411_1.tb411_1;
import static project.honey.business.entity.manage.QTb412.tb412;
import static project.honey.business.entity.manage.QTb412_1.tb412_1;

public class Tb412RepositoryDslImpl implements Tb412RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb412RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb412> findAllByDsl(String ymd1, String ymd2, Search040203 search040203, List<Integer> seqList, Pageable pageable) {
        List<Tb412> result = queryFactory.select(tb412)
                .from(tb412)
                .leftJoin(tb412.tb412_1s, tb412_1).fetchJoin()
                .where(
                        saleDtEq(ymd1, ymd2),
                        nameContain(search040203.getSName()),
                        addressContain(search040203.getSAddress()),
                        custEq(search040203.getSCustCd()),
                        goodsSeqIn(search040203.getSGoodsCd(), seqList),
                        noteContain(search040203.getSNote()),
                        note2Contain(search040203.getSNote2()),
                        empEq(search040203.getSEmpNo()),
                        projectEq(search040203.getSProjectCd()),
                        statusEq(search040203.getSStatus()),
                        whouseEq(search040203.getSWhouseCd())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        tb412.seq.asc(),
                        tb412_1.seq.asc()
                )
                .fetch();

        int total = queryFactory.select(tb412)
                .from(tb412)
                .leftJoin(tb412.tb412_1s, tb412_1).fetchJoin()
                .where(
                        saleDtEq(ymd1, ymd2),
                        nameContain(search040203.getSName()),
                        addressContain(search040203.getSAddress()),
                        custEq(search040203.getSCustCd()),
                        goodsSeqIn(search040203.getSGoodsCd(), seqList),
                        noteContain(search040203.getSNote()),
                        note2Contain(search040203.getSNote2()),
                        empEq(search040203.getSEmpNo()),
                        projectEq(search040203.getSProjectCd()),
                        statusEq(search040203.getSStatus()),
                        whouseEq(search040203.getSWhouseCd())
                )
                .fetch()
                .size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb412> findAllByExcel(String ymd1, String ymd2, Search040203 search040203, List<Integer> seqList) {
        List<Tb412> result = queryFactory.select(tb412)
                .from(tb412)
                .leftJoin(tb412.tb412_1s, tb412_1).fetchJoin()
                .where(
                        saleDtEq(ymd1, ymd2),
                        nameContain(search040203.getSName()),
                        addressContain(search040203.getSAddress()),
                        custEq(search040203.getSCustCd()),
                        goodsSeqIn(search040203.getSGoodsCd(), seqList),
                        noteContain(search040203.getSNote()),
                        note2Contain(search040203.getSNote2()),
                        empEq(search040203.getSEmpNo()),
                        projectEq(search040203.getSProjectCd()),
                        statusEq(search040203.getSStatus()),
                        whouseEq(search040203.getSWhouseCd())
                )
                .orderBy(
                        tb412.seq.asc(),
                        tb412_1.seq.asc()
                )
                .fetch();

        return result;
    }

    @Override
    public List<Tb412> findAllByPopUp(String ymd1, String ymd2, SearchPopUp410 searchPopUp410, List<Integer> seqList) {
        List<Tb412> result = queryFactory.select(tb412)
                .from(tb412)
                .leftJoin(tb412.tb412_1s, tb412_1).fetchJoin()
                .where(
                        saleDtEq(ymd1, ymd2),
                        custEq(searchPopUp410.getSCustCd()),
                        goodsSeqIn(searchPopUp410.getSGoodsCd(), seqList)
                )
                .orderBy(
                        tb412.seq.asc(),
                        tb412_1.seq.asc()
                )
                .fetch();

        return result;
    }

    @Override
    public Page<Tb412> findAllBy040301(String ymd1, String ymd2, Search040301 search040301, List<String> custList, Pageable pageable) {
        List<Tb412> result = queryFactory.select(tb412)
                .from(tb412)
                .leftJoin(tb412.tb412_1s, tb412_1).fetchJoin()
                .where(
                        saleDtEq(ymd1, ymd2),
                        custEq(search040301.getSCustCd()),
                        goodsEq(search040301.getSGoodsCd()),
                        projectEq(search040301.getSProjectCd()),
                        custGrEq(search040301.getSCustGr(), custList)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        tb412.seq.asc(),
                        tb412_1.seq.asc()
                )
                .fetch();

        int total = queryFactory.select(tb412)
                .from(tb412)
                .leftJoin(tb412.tb412_1s, tb412_1).fetchJoin()
                .where(
                        saleDtEq(ymd1, ymd2),
                        custEq(search040301.getSCustCd()),
                        goodsEq(search040301.getSGoodsCd()),
                        projectEq(search040301.getSProjectCd()),
                        custGrEq(search040301.getSCustGr(), custList)
                )
                .fetch()
                .size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb412> findAllBy040301Excel(String ymd1, String ymd2, Search040301 search040301, List<String> custList) {
        List<Tb412> result = queryFactory.select(tb412)
                .from(tb412)
                .leftJoin(tb412.tb412_1s, tb412_1).fetchJoin()
                .where(
                        saleDtEq(ymd1, ymd2),
                        custEq(search040301.getSCustCd()),
                        goodsEq(search040301.getSGoodsCd()),
                        projectEq(search040301.getSProjectCd()),
                        custGrEq(search040301.getSCustGr(), custList)
                )
                .orderBy(
                        tb412.seq.asc(),
                        tb412_1.seq.asc()
                )
                .fetch();

        return result;
    }

    @Override
    public Page<Tb412> findAllBy040306(String ymd1, String ymd2, Search040306 search040306, List<Integer> seqList, Pageable pageable) {
        List<Tb412> result = queryFactory.select(tb412)
                .from(tb412)
                .leftJoin(tb412.tb412_1s, tb412_1).fetchJoin()
                .where(
                        saleDtEq(ymd1, ymd2),
                        custEq(search040306.getSCustCd()),
                        goodsSeqIn(search040306.getSGoodsCd(), seqList)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        tb412.seq.asc(),
                        tb412_1.seq.asc()
                )
                .fetch();

        int total = queryFactory.select(tb412)
                .from(tb412)
                .leftJoin(tb412.tb412_1s, tb412_1).fetchJoin()
                .where(
                        saleDtEq(ymd1, ymd2),
                        custEq(search040306.getSCustCd()),
                        goodsSeqIn(search040306.getSGoodsCd(), seqList)
                )
                .fetch()
                .size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb412> findAllBy040306Excel(String ymd1, String ymd2, Search040306 search040306, List<Integer> seqList) {
        List<Tb412> result = queryFactory.select(tb412)
                .from(tb412)
                .leftJoin(tb412.tb412_1s, tb412_1).fetchJoin()
                .where(
                        saleDtEq(ymd1, ymd2),
                        custEq(search040306.getSCustCd()),
                        goodsSeqIn(search040306.getSGoodsCd(), seqList)
                )
                .orderBy(
                        tb412.seq.asc(),
                        tb412_1.seq.asc()
                )
                .fetch();

        return result;
    }

    private BooleanExpression saleDtEq(String ymd1, String ymd2){
        return StringUtils.hasText(ymd1) && StringUtils.hasText(ymd2) ? tb412.saleDt.between(ymd1, ymd2) : null;
    }

    private BooleanExpression nameContain(String name){
        return StringUtils.hasText(name) ? tb412.name.contains(name) : null;
    }

    private BooleanExpression addressContain(String address){
        return StringUtils.hasText(address) ? tb412.address.contains(address) : null;
    }

    private BooleanExpression custEq(String custCd){
        return StringUtils.hasText(custCd) ? tb412.custCd.eq(custCd) : null;
    }

    private BooleanExpression goodsSeqIn(String goodsCd, List<Integer> seqList){
        return StringUtils.hasText(goodsCd) ? tb412.seq.in(seqList) : null;
    }

    private BooleanExpression noteContain(String note){
        return StringUtils.hasText(note) ? tb412.note.contains(note) : null;
    }

    private BooleanExpression note2Contain(String note2){
        return StringUtils.hasText(note2) ? tb412.note2.contains(note2) : null;
    }

    private BooleanExpression empEq(String empNo){
        return StringUtils.hasText(empNo) ? tb412.empNo.eq(empNo) : null;
    }

    private BooleanExpression projectEq(String projectCd){
        return StringUtils.hasText(projectCd) ? tb412.projectCd.eq(projectCd) : null;
    }

    private BooleanExpression statusEq(String status){
        return StringUtils.hasText(status) ? tb412.status.eq(status) : null;
    }

    private BooleanExpression whouseEq(String whouseCd){
        return StringUtils.hasText(whouseCd) ? tb412.whouseCd.eq(whouseCd) : null;
    }

    private BooleanExpression custGrEq(String custGr, List<String> custList){
        return StringUtils.hasText(custGr) ? tb412.custCd.in(custList) : null;
    }

    private BooleanExpression goodsEq(String goodsCd){
        return StringUtils.hasText(goodsCd) ? tb412_1.goodsCd.eq(goodsCd) : null;
    }
}
