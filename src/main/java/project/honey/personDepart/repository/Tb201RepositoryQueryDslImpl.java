package project.honey.personDepart.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.StringUtils;
import project.honey.personDepart.entity.Tb201;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.personDepart.entity.QTb201.tb201;

public class Tb201RepositoryQueryDslImpl implements Tb201RepositoryQueryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb201RepositoryQueryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Tb201> findAllByDsl(String empNm, String postCd, String deptCd) {

        List<Tb201> result = queryFactory.select(tb201)
                .from(tb201)
                .where(
                        empNmContains(empNm),
                        postCdEq(postCd),
                        deptCdEq(deptCd)
                )
                .fetch();

        return result;
    }

    private BooleanExpression empNmContains(String empNm) {
        return StringUtils.hasText(empNm) ? tb201.empNm.contains(empNm) : null;
    }

    private BooleanExpression postCdEq(String postCd) {
        return StringUtils.hasText(postCd) ? tb201.post.eq(postCd) : null;
    }

    private BooleanExpression deptCdEq(String deptCd) {
        return StringUtils.hasText(deptCd) ? tb201.deptCd.eq(deptCd) : null;
    }
}
