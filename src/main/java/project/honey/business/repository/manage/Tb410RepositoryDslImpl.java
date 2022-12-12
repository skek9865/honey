package project.honey.business.repository.manage;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class Tb410RepositoryDslImpl implements Tb410RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb410RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


}
