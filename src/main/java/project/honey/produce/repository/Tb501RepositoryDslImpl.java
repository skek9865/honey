package project.honey.produce.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.honey.produce.entity.QTb501;
import project.honey.produce.entity.Tb501;

import javax.persistence.EntityManager;

import java.util.List;

import static project.honey.produce.entity.QTb501.*;

public class Tb501RepositoryDslImpl implements Tb501RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb501RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<Tb501> findAllByDsl(Pageable pageable) {
        List<Tb501> result = queryFactory.selectFrom(tb501)
                .orderBy(tb501.productAl.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.selectFrom(tb501)
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb501> findAllByExcel() {
        return queryFactory.selectFrom(tb501)
                .orderBy(tb501.productAl.asc())
                .fetch();
    }
}
