package project.honey.pay.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.pay.dto.QTb301Dto;
import project.honey.pay.dto.Tb301Dto;
import project.honey.pay.entity.QTb301;
import project.honey.pay.entity.Tb301;
import project.honey.system.entity.Tb906;

import javax.persistence.EntityManager;

import java.util.List;

import static project.honey.pay.entity.QTb301.*;
import static project.honey.system.entity.QTb906.tb906;


public class Tb301RepositoryDslImpl implements Tb301RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb301RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb301Dto> findAllByDsl(Pageable pageable) {
        List<Tb301Dto> result = queryFactory
                .select(new QTb301Dto(
                        tb301.seq,
                        tb906.codeNm,
                        tb301.taxDiv,
                        tb301.itemCd,
                        tb301.itemNm,
                        tb301.taxRate,
                        tb301.useYn))
                .from(tb301)
                .leftJoin(tb906).on(tb301.itemDiv.eq(tb906.scdId))
                .where(fstIdEq("05"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(tb301.seq.asc())
                .fetch();

        int total = queryFactory.selectFrom(tb301)
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<String> findAllByUseItemNm() {
        return queryFactory.select(tb301.itemNm)
                .from(tb301)
                .where(tb301.useYn.eq("Y"))
                .orderBy(tb301.itemCd.asc())
                .fetch();
    }

    @Override
    public List<String> findAllByUseItemCd() {
        return queryFactory.select(tb301.itemCd)
                .from(tb301)
                .where(tb301.useYn.eq("Y"))
                .orderBy(tb301.itemCd.asc())
                .fetch();
    }


    private BooleanExpression fstIdEq(String fstId) {
        return StringUtils.hasText(fstId) ? tb906.fstId.eq(fstId) : null;
    }
}
