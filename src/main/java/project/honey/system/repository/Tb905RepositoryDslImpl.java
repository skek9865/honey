package project.honey.system.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.honey.system.entity.QTb905;
import project.honey.system.entity.Tb903;
import project.honey.system.entity.Tb905;

import javax.persistence.EntityManager;

import java.util.List;

import static project.honey.system.entity.QTb903.tb903;
import static project.honey.system.entity.QTb905.*;

public class Tb905RepositoryDslImpl implements Tb905RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb905RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb905> findAllByDsl(Pageable pageable) {
        List<Tb905> result = queryFactory.selectFrom(tb905)
                .orderBy(tb905.tpId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.selectFrom(tb905)
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }
}
