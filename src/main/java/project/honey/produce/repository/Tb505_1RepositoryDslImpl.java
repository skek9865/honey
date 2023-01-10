package project.honey.produce.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.honey.business.entity.basic.QTb405;
import project.honey.produce.dto.QTb505_1Dto;
import project.honey.produce.dto.Tb505_1Dto;
import project.honey.produce.entity.QTb505_1;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.business.entity.basic.QTb405.*;
import static project.honey.produce.entity.QTb505_1.*;

public class Tb505_1RepositoryDslImpl implements Tb505_1RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb505_1RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Tb505_1Dto> findAllByDtos(Integer fk) {

        return queryFactory.select(
                        new QTb505_1Dto(
                                tb505_1.seq,
                                tb505_1.tb505.seq,
                                tb505_1.goodsCd,
                                tb405.goodsNm,
                                tb505_1.qty,
                                tb505_1.oQty,
                                tb505_1.note
                        ))
                .from(tb505_1)
                .leftJoin(tb405).on(tb505_1.goodsCd.eq(tb405.goodsCd))
                .where(tb505_1.tb505.seq.eq(fk))
                .orderBy(tb505_1.seq.asc())
                .fetch();
    }
}
