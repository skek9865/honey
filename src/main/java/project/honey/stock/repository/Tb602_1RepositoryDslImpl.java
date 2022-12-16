package project.honey.stock.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.honey.stock.dto.QTb602_1Dto;
import project.honey.stock.dto.Tb602_1Dto;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.business.entity.basic.QTb405.tb405;
import static project.honey.stock.entity.QTb602_1.*;


public class Tb602_1RepositoryDslImpl implements Tb602_1RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb602_1RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Tb602_1Dto> findAllByDtos(Integer fk) {
        return queryFactory.select(
                        new QTb602_1Dto(
                                tb602_1.seq,
                                tb602_1.tb602.seq,
                                tb602_1.goodsCd,
                                tb405.goodsNm,
                                tb602_1.qty,
                                tb602_1.useType,
                                tb602_1.note
                        ))
                .from(tb602_1)
                .leftJoin(tb405).on(tb602_1.goodsCd.eq(tb405.goodsCd))
                .where(tb602_1.tb602.seq.eq(fk))
                .orderBy(tb602_1.seq.asc())
                .fetch();
    }
}
