package project.honey.stock.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.honey.produce.dto.QTb505_1Dto;
import project.honey.stock.dto.QTb601_1Dto;
import project.honey.stock.dto.Tb601_1Dto;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.business.entity.basic.QTb405.tb405;
import static project.honey.stock.entity.QTb601_1.*;


public class Tb601_1RepositoryDslImpl implements Tb601_1RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb601_1RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Tb601_1Dto> findAllByDtos(Integer fk) {
        return queryFactory.select(
                        new QTb601_1Dto(
                                tb601_1.seq,
                                tb601_1.tb601.seq,
                                tb601_1.goodsCd,
                                tb405.goodsNm,
                                tb601_1.qty,
                                tb601_1.rQty,
                                tb601_1.note
                        ))
                .from(tb601_1)
                .leftJoin(tb405).on(tb601_1.goodsCd.eq(tb405.goodsCd))
                .where(tb601_1.tb601.seq.eq(fk))
                .orderBy(tb601_1.seq.asc())
                .fetch();
    }
}
