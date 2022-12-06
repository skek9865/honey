package project.honey.business.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.honey.business.dto.Search040105;
import project.honey.business.entity.Tb405;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.business.entity.QTb405.*;

public class Tb405RepositoryDslImpl implements Tb405RepositoryDsl{

    private final JPAQueryFactory queryFactory;

    public Tb405RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb405> findAllByDsl(Search040105 search, Pageable pageable) {

        List<Tb405> result = queryFactory.selectFrom(tb405)
                .where(
                        goodsNmContains(search.getGoodsNm()),
                        itemGb1Eq(search.getItemGb1())
                )
                .orderBy(tb405.seq.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory.selectFrom(tb405)
                .where(
                        goodsNmContains(search.getGoodsNm()),
                        itemGb1Eq(search.getItemGb1())
                ).fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Tb405> findAllByExcel(Search040105 search) {
        return queryFactory.selectFrom(tb405)
                .where(
                        goodsNmContains(search.getGoodsNm()),
                        itemGb1Eq(search.getItemGb1())
                )
                .orderBy(tb405.seq.asc())
                .fetch();
    }

    private BooleanExpression goodsNmContains(String goodsNm){
        return StringUtils.hasText(goodsNm) ? tb405.goodsNm.contains(goodsNm) : null;
    }

    private BooleanExpression itemGb1Eq(String itemGb1){
        return StringUtils.hasText(itemGb1) ? tb405.itemGb1.eq(itemGb1) : null;
    }
}
