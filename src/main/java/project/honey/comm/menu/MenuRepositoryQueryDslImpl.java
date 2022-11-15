//package project.honey.comm.menu;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//public class MenuRepositoryQueryDslImpl implements MenuRepositoryQueryDsl{
//
//    private final JPAQueryFactory queryFactory;
//
//    public MenuRepositoryQueryDslImpl(EntityManager em) {
//        this.queryFactory = new JPAQueryFactory(em);
//    }
//
//    @Override
//    public List<MenuIdDto> getMenuId() {
//
//        queryFactory.select(*)
//
//        return null;
//    }
//}
