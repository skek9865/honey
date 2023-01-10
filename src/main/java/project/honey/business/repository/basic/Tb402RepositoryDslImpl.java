package project.honey.business.repository.basic;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.business.dto.basic.QTb402Popup;
import project.honey.business.dto.basic.Tb402Popup;
import project.honey.business.entity.basic.Tb402;

import javax.persistence.EntityManager;

import java.util.List;

import static project.honey.business.entity.basic.QTb402.tb402;
import static project.honey.personDepart.entity.QTb201.*;

public class Tb402RepositoryDslImpl implements Tb402RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb402RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb402> findAllByDsl(String custNm, String ceoNm, String empCd, String class1, Pageable pageable) {

        List<Tb402> result = queryFactory.select(tb402)
                .from(tb402)
                .where(
                        custNmContains(custNm),
                        ceoNmContains(ceoNm),
                        empCdEq(empCd),
                        class1Eq(class1)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.select(tb402)
                .from(tb402)
                .where(
                        custNmContains(custNm),
                        ceoNmContains(ceoNm),
                        empCdEq(empCd),
                        class1Eq(class1)
                )
                .fetch()
                .size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb402> findAllByExcel(String custNm, String ceoNm, String empCd, String class1) {
        List<Tb402> result = queryFactory.select(tb402)
                .from(tb402)
                .where(
                        custNmContains(custNm),
                        ceoNmContains(ceoNm),
                        empCdEq(empCd),
                        class1Eq(class1)
                )
                .fetch();

        return result;
    }

    @Override
    public List<Tb402Popup> findAllByPopup(String custNm) {
        return queryFactory.select(
                        new QTb402Popup(
                                tb402.seq,
                                tb402.custCd,
                                tb402.custNm,
                                tb402.ceoNm,
                                tb201.empNm
                        ))
                .from(tb402)
                .leftJoin(tb201).on(tb402.empCd.eq(tb201.empNo))
                .where(custNmContains(custNm))
                .fetch();
    }

    private BooleanExpression custNmContains(String custNm){
        return StringUtils.hasText(custNm) ? tb402.custNm.contains(custNm) : null;
    }

    private BooleanExpression ceoNmContains(String ceoNm){
        return StringUtils.hasText(ceoNm) ? tb402.ceoNm.contains(ceoNm) : null;
    }

    private BooleanExpression empCdEq(String empCd){
        return StringUtils.hasText(empCd) ? tb402.empCd.eq(empCd) : null;
    }

    private BooleanExpression class1Eq(String class1){
        return StringUtils.hasText(class1) ? tb402.class1.eq(class1) : null;
    }
}
