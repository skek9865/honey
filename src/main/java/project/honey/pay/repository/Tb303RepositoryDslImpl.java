package project.honey.pay.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.honey.pay.entity.QTb303;
import project.honey.pay.entity.Tb303;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.pay.entity.QTb301.tb301;
import static project.honey.pay.entity.QTb303.*;

public class Tb303RepositoryDslImpl implements Tb303RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb303RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Tb303> findAllByUsePay(String empNo, String payDt) {
        return queryFactory.select(tb303)
                .from(tb303)
                .leftJoin(tb301).on(tb303.itemCd.eq(tb301.itemCd))
                .where(
                        tb301.useYn.eq("Y"),
                        tb303.empNo.eq(empNo),
                        tb303.payDt.eq(payDt)
                )
                .fetch();
    }
}
