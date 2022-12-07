package project.honey.system.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.honey.system.entity.QTb903;
import project.honey.system.entity.Tb903;

import javax.persistence.EntityManager;

import java.util.List;

import static project.honey.system.entity.QTb903.*;

public class Tb903RepositoryDslImpl implements Tb903RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb903RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb903> findAllByDsl(Pageable pageable) {
        List<Tb903> result = queryFactory.selectFrom(tb903)
                .orderBy(tb903.tpId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.selectFrom(tb903)
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }
}
