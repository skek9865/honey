package project.honey.personDepart.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.personDepart.entity.Tb204;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.personDepart.entity.QTb204.tb204;

public class Tb204RepositoryDslImpl implements Tb204RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb204RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb204> findAllByDsl(String outFNm, String part, Pageable pageable) {

        List<Tb204> result = queryFactory.select(tb204)
                .from(tb204)
                .where(
                        outFNmContains(outFNm),
                        partCdEq(part)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.select(tb204)
                .from(tb204)
                .where(
                        outFNmContains(outFNm),
                        partCdEq(part)
                )
                .fetch().size();

        return new PageImpl<>(result,pageable,total);
    }

    @Override
    public List<Tb204> findAllByExcel(String outFNm, String part) {
        List<Tb204> result = queryFactory.select(tb204)
                .from(tb204)
                .where(
                        outFNmContains(outFNm),
                        partCdEq(part)
                )
                .fetch();
        return result;
    }

    private BooleanExpression outFNmContains(String outFNm) {
        return StringUtils.hasText(outFNm) ? tb204.outFNm.contains(outFNm) : null;
    }

    private BooleanExpression partCdEq(String part) {
        return StringUtils.hasText(part) ? tb204.part.eq(part) : null;
    }
}
