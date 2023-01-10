package project.honey.personDepart.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.personDepart.entity.Tb203;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.personDepart.entity.QTb203.tb203;

public class Tb203RepositoryDslImpl implements Tb203RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb203RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb203> findAllByDsl(String outFNm, String part, Pageable pageable) {

        List<Tb203> result = queryFactory.select(tb203)
                .from(tb203)
                .where(
                        outFNmContains(outFNm),
                        partCdEq(part)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.select(tb203)
                .from(tb203)
                .where(
                        outFNmContains(outFNm),
                        partCdEq(part)
                )
                .fetch().size();

        return new PageImpl<>(result,pageable,total);
    }

    @Override
    public List<Tb203> findAllByExcel(String outFNm, String part) {
        List<Tb203> result = queryFactory.select(tb203)
                .from(tb203)
                .where(
                        outFNmContains(outFNm),
                        partCdEq(part)
                )
                .fetch();
        return result;
    }

    private BooleanExpression outFNmContains(String outFNm) {
        return StringUtils.hasText(outFNm) ? tb203.outFNm.contains(outFNm) : null;
    }

    private BooleanExpression partCdEq(String part) {
        return StringUtils.hasText(part) ? tb203.part.eq(part) : null;
    }
}
