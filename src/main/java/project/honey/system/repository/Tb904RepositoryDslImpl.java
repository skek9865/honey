package project.honey.system.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.system.entity.QTb904;
import project.honey.system.entity.Tb904;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.system.entity.QTb904.*;


public class Tb904RepositoryDslImpl implements Tb904RepositoryDsl {

    private final JPAQueryFactory queryFactory;

    public Tb904RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Tb904> getMenuId(Integer type, String fcd, String scd, String userId) {

        List<Tb904> result =
                queryFactory.select(tb904)
                .from(tb904)
                .where(
                        makeMenu(type, fcd, scd, userId)
                )
                .fetch();

        return result;
    }

    @Override
    public Page<Tb904> findAllByDsl(Pageable pageable) {

        List<Tb904> result = queryFactory.selectFrom(tb904)
                .orderBy(tb904.fstId.asc(), tb904.scdId.asc(), tb904.thdId.asc(), tb904.alien.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.selectFrom(tb904)
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb904> findAllByExcel() {
        return queryFactory.selectFrom(tb904)
                .orderBy(tb904.fstId.asc(), tb904.scdId.asc(), tb904.thdId.asc(), tb904.alien.asc())
                .fetch();
    }

    private BooleanExpression makeMenu(Integer type, String fcd, String scd, String userId){
        if(type != null){
            switch (type){
                case 1:
                    return tb904.scdId.eq("00").and(tb904.useYn.eq("Y"));
                case 2:
                    if(StringUtils.hasText(fcd)){
                        return tb904.fstId.eq(fcd).and(tb904.useYn.eq("Y"));
                    }
            }
        }
        return null;

    }
}
