package project.honey.business.repository.sale;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.business.entity.sale.Tb416;
import project.honey.business.entity.sale.Tb417;
import project.honey.business.form.analyze.Search040307;
import project.honey.business.form.sale.Search040402;
import project.honey.business.form.sale.Search040404;

import javax.persistence.EntityManager;

import java.util.List;

import static project.honey.business.entity.sale.QTb417.tb417;

public class Tb417RepositoryDslImpl implements Tb417RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb417RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb417> findAllBy040404(String ymd1, String ymd2, Search040404 search040404, Pageable pageable) {
        List<Tb417> result = queryFactory.selectFrom(tb417)
                .where(
                        tb417.amountCl.eq("00002"),
                        amountDtEq(ymd1, ymd2),
                        custCdEq(search040404.getSCustCd())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.selectFrom(tb417)
                .where(
                        tb417.amountCl.eq("00002"),
                        amountDtEq(ymd1, ymd2),
                        custCdEq(search040404.getSCustCd())
                )
                .fetch()
                .size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb417> findAllBy040404Excel(String ymd1, String ymd2, Search040404 search040404) {
        List<Tb417> result = queryFactory.selectFrom(tb417)
                .where(
                        tb417.amountCl.eq("00002"),
                        amountDtEq(ymd1, ymd2),
                        custCdEq(search040404.getSCustCd())
                )
                .fetch();

        return result;
    }

    private BooleanExpression amountDtEq(String ymd1, String ymd2){
        return StringUtils.hasText(ymd1) && StringUtils.hasText(ymd2) ? tb417.amountDt.between(ymd1, ymd2) : null;
    }

    private BooleanExpression custCdEq(String custCd){
        return StringUtils.hasText(custCd) ? tb417.custCd.eq(custCd) : null;
    }
}
