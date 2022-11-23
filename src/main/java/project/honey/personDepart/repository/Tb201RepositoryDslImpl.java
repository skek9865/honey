package project.honey.personDepart.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.personDepart.entity.Tb201;
import project.honey.system.entity.QTb906;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.personDepart.entity.QTb201.tb201;
import static project.honey.system.entity.QTb906.*;

public class Tb201RepositoryDslImpl implements Tb201RepositoryDsl {

    private final JPAQueryFactory queryFactory;

    public Tb201RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb201> findAllByDsl(String empNm, String postCd, String deptCd, Pageable pageable) {

        List<Tb201> result = queryFactory.select(tb201)
                .from(tb201)
                .where(
                        empNmContains(empNm),
                        postCdEq(postCd),
                        deptCdEq(deptCd)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.select(tb201)
                .from(tb201)
                .where(
                        empNmContains(empNm),
                        postCdEq(postCd),
                        deptCdEq(deptCd)
                )
                .fetch().size();

        return new PageImpl<>(result,pageable,total);
    }

    @Override
    public Page<Tb201> findAllByLeave(String empNm, String postCd, String deptCd, Pageable pageable) {
        List<Tb201> result = queryFactory.select(tb201)
                .from(tb201)
                .where(
                        empNmContains(empNm),
                        postCdEq(postCd),
                        deptCdEq(deptCd),
                        tb201.leaveDt.eq("")
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.select(tb201)
                .from(tb201)
                .where(
                        empNmContains(empNm),
                        postCdEq(postCd),
                        deptCdEq(deptCd),
                        tb201.leaveDt.eq("")
                )
                .fetch().size();

        return new PageImpl<>(result,pageable,total);
    }

    @Override
    public List<Tb201> findAllByExcel(String empNm, String postCd, String deptCd) {
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
