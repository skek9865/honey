package project.honey.comm.menu;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.comm.menu.QTb904.tb904;

public class MenuRepositoryQueryDslImpl implements MenuRepositoryQueryDsl{

    private final JPAQueryFactory queryFactory;

    public MenuRepositoryQueryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Tb904> getMenuId(Integer type, String fcd, String scd, String userId) {

        List<Tb904> result =
                queryFactory.select(tb904)
                .from(tb904)
                .where(
                        makeMenu(type, fcd, scd, userId)
                )
                .fetch();

        return result;
    }

    public List<Tb904> findMenuNm(String fstId){

        List<Tb904> result = queryFactory.select(tb904)
                .from(tb904)
                .where(tb904.fstId.eq(fstId))
                .fetch();
        return result;

    }

    private BooleanExpression makeMenu(Integer type, String fcd, String scd, String userId){
        if(type != null){
            switch (type){
                case 1:
                    return tb904.scdId.eq("00").and(tb904.useYn.eq("Y"));
                case 2:
                    if(StringUtils.hasText(fcd)){
                        return tb904.fstId.eq(fcd).and(tb904.thdId.eq("00")).and(tb904.useYn.eq("Y"));
                    }
                case 3:
                    if(StringUtils.hasText(fcd) && StringUtils.hasText(scd)){
                        return tb904.fstId.eq(fcd).and(tb904.scdId.eq(scd)).and(tb904.useYn.eq("Y"));
                    }
            }
        }
        return null;

    }
}
