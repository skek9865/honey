package project.honey.company.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.honey.company.entity.Tb105;


import javax.persistence.EntityManager;


import java.util.List;
import java.util.stream.Collectors;

import static project.honey.company.entity.QTb105.tb105;


public class Tb105RepositoryDslImpl implements Tb105RepositoryDsl{
    private final JPAQueryFactory queryFactory;

    public Tb105RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Tb105> findAllByDsl() {
        //left join으로 인한 중복 컬럼제거
        return queryFactory.select(tb105)
                .from(tb105)
                .leftJoin(tb105.tb105_1List).fetchJoin()
                .fetch().stream().distinct().collect(Collectors.toList());
    }

    @Override
    public Page<Tb105> findAllByDsl(Pageable pageable) {
        List<Tb105> fetch = queryFactory.select(tb105)
                .from(tb105)
                .leftJoin(tb105.tb105_1List).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int size = queryFactory.selectFrom(tb105).fetch().size();

        return new PageImpl<>(fetch,pageable,size);
    }
}
