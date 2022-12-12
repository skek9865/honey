package project.honey.business.repository.manage;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class Tb410_1RepositoryDslImpl implements Tb410_1RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb410_1RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
