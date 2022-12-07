package project.honey.produce.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.honey.business.entity.QTb405;
import project.honey.produce.dto.QTb503_1Dto;
import project.honey.produce.dto.Tb503_1Dto;
import project.honey.produce.dto.input.Tb503Input;
import project.honey.produce.entity.QTb501;
import project.honey.produce.entity.QTb503;
import project.honey.produce.entity.QTb503_1;
import project.honey.produce.entity.Tb502;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.business.entity.QTb405.*;
import static project.honey.produce.entity.QTb501.*;
import static project.honey.produce.entity.QTb502.tb502;
import static project.honey.produce.entity.QTb503.*;
import static project.honey.produce.entity.QTb503_1.*;

public class Tb503_1RepositoryDslImpl implements Tb503_1RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb503_1RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Tb503_1Dto> findAllByDtos(Integer fk) {
        return queryFactory.select(
                        new QTb503_1Dto(
                                tb503_1.seq,
                                tb503_1.tb503.seq,
                                tb503_1.goodsCd,
                                tb405.goodsNm,
                                tb405.standard,
                                tb405.unit,
                                tb503_1.qty,
                                tb501.productNm,
                                tb503_1.location,
                                tb503_1.note
                        ))
                .from(tb503_1).leftJoin(tb405).on(tb503_1.goodsCd.eq(tb405.goodsCd))
                .leftJoin(tb501).on(tb503_1.productCd.eq(tb501.productCd))
                .where(tb503_1.tb503.seq.eq(fk))
                .fetch();
    }
}
