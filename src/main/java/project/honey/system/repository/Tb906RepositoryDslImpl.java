package project.honey.system.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.system.dto.CodeDto;
import project.honey.system.dto.QCodeDto;
import project.honey.system.entity.Tb906;

import javax.persistence.EntityManager;

import java.util.List;

import static project.honey.system.entity.QTb906.*;

public class Tb906RepositoryDslImpl implements Tb906RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb906RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb906> findAllByDsl(String fstId, Pageable pageable) {

        List<Tb906> result = queryFactory.selectFrom(tb906)
                .where(
                    fstIdEq(fstId)
                )
                .orderBy(tb906.fstId.asc(), tb906.scdId.asc(), tb906.alien.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.selectFrom(tb906)
                .where(
                    fstIdEq(fstId)
                )
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<CodeDto> findByFstIdByDsl(String fstId) {
        return queryFactory
                .select(new QCodeDto(
                        tb906.codeNm,
                        tb906.scdId
                        ))
                .from(tb906)
                .where(
                        fstIdEq(fstId), tb906.scdId.ne("00000")
                )
                .orderBy(tb906.alien.asc())
                .fetch();
    }

    @Override
    public List<CodeDto> findFstIdAllByDsl() {
        return queryFactory
                .select(new QCodeDto(
                        tb906.codeNm,
                        tb906.fstId
                ))
                .from(tb906)
                .where(tb906.scdId.eq("00000"))
                .orderBy(tb906.fstId.asc())
                .fetch();
    }

    @Override
    public List<Tb906> findAllByExcel(String fstId) {
        return queryFactory.selectFrom(tb906)
                .where(
                        fstIdEq(fstId)
                )
                .orderBy(tb906.fstId.asc(), tb906.scdId.asc(), tb906.alien.asc())
                .fetch();
    }

    private BooleanExpression fstIdEq(String fstId) {
        return StringUtils.hasText(fstId) ? tb906.fstId.eq(fstId) : null;
    }

}
