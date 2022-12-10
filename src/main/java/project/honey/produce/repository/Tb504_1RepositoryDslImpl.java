package project.honey.produce.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.honey.business.entity.QTb405;
import project.honey.produce.dto.QTb504_1Dto;
import project.honey.produce.dto.Tb504_1Dto;
import project.honey.produce.entity.QTb504_1;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.business.entity.QTb405.*;
import static project.honey.produce.entity.QTb504_1.*;

public class Tb504_1RepositoryDslImpl implements Tb504_1RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb504_1RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Tb504_1Dto> findAllByDtos(Integer fk) {
        return queryFactory.select(
                        new QTb504_1Dto(
                                tb504_1.seq,
                                tb504_1.tb504.seq,
                                tb504_1.goodsCd,
                                tb405.goodsNm,
                                tb405.standard,
                                tb504_1.qty,
                                tb504_1.rQty,
                                tb504_1.status,
                                tb504_1.machineNo,
                                tb504_1.empNo,
                                tb504_1.note
                        ))
                .from(tb504_1)
                .leftJoin(tb405).on(tb504_1.goodsCd.eq(tb405.goodsCd))
                .where(tb504_1.tb504.seq.eq(fk))
                .orderBy(tb504_1.seq.asc())
                .fetch();
    }
}
