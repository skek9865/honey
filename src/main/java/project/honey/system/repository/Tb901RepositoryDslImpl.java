package project.honey.system.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.system.dto.QTb901Dto;
import project.honey.system.dto.Tb901Dto;

import javax.persistence.EntityManager;

import java.util.List;

import static project.honey.personDepart.entity.QTb201.*;
import static project.honey.system.entity.QTb901.*;
import static project.honey.system.entity.QTb906.*;

public class Tb901RepositoryDslImpl implements Tb901RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb901RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb901Dto> findAllByDsl(Pageable pageable) {
        List<Tb901Dto> result = queryFactory
                .select(
                        new QTb901Dto(
                                tb901.userId,
                                tb901.passwd,
                                tb901.userNm,
                                tb901.phone,
                                tb901.mobile,
                                tb901.email,
                                tb906.codeNm,
                                tb901.useYn,
                                tb901.empYn,
                                tb201.empNm,
                                tb901.regDt
                        ))
                .from(tb901)
                .leftJoin(tb906).on(tb901.userGr.eq(tb906.scdId))
                .leftJoin(tb201).on(tb901.empNo.eq(tb201.empNo))
                .where(fstIdEq("99"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(tb906.createDate.asc())
                .fetch();


        int total = queryFactory.selectFrom(tb901)
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    private BooleanExpression fstIdEq(String fstId) {
        return StringUtils.hasText(fstId) ? tb906.fstId.eq(fstId) : null;
    }
}
