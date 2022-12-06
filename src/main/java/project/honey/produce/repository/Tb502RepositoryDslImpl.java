package project.honey.produce.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.honey.produce.entity.QTb502;
import project.honey.produce.entity.Tb501;
import project.honey.produce.entity.Tb502;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.produce.entity.QTb501.tb501;
import static project.honey.produce.entity.QTb502.*;

public class Tb502RepositoryDslImpl implements Tb502RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb502RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb502> findAllByDsl(Pageable pageable) {
        List<Tb502> result = queryFactory.selectFrom(tb502)
                .orderBy(tb502.errorAl.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.selectFrom(tb502)
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb502> findAllByExcel() {
        return queryFactory.selectFrom(tb502)
                .orderBy(tb502.errorAl.asc())
                .fetch();
    }
}
