package project.honey.produce.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.honey.produce.dto.QTb505_1Dto;
import project.honey.produce.dto.QTb506_1Dto;
import project.honey.produce.dto.Tb505_1Dto;
import project.honey.produce.dto.Tb506_1Dto;
import project.honey.produce.entity.QTb506_1;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.business.entity.basic.QTb405.tb405;
import static project.honey.produce.entity.QTb505_1.tb505_1;
import static project.honey.produce.entity.QTb506_1.*;

public class Tb506_1RepositoryDslImpl implements Tb506_1RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb506_1RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Tb506_1Dto> findAllByDtos(Integer fk) {

        return queryFactory.select(
                        new QTb506_1Dto(
                                tb506_1.seq,
                                tb506_1.tb506.seq,
                                tb506_1.goodsCd,
                                tb405.goodsNm,
                                tb506_1.qty,
                                tb405.wPrice,
                                tb506_1.standard,
                                tb506_1.note
                        ))
                .from(tb506_1)
                .leftJoin(tb405).on(tb506_1.goodsCd.eq(tb405.goodsCd))
                .where(tb506_1.tb506.seq.eq(fk))
                .orderBy(tb506_1.seq.asc())
                .fetch();
    }
}
