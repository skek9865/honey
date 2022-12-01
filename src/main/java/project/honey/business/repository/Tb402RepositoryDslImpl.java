package project.honey.business.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class Tb402RepositoryDslImpl implements Tb402RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb402RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
