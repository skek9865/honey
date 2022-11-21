package project.honey.pay.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.pay.dto.QTb302ResultDto;
import project.honey.pay.dto.Tb302ResultDto;
import project.honey.pay.entity.QTb302;
import project.honey.pay.entity.Tb302;
import project.honey.personDepart.entity.QTb201;

import javax.persistence.EntityManager;

import static project.honey.pay.entity.QTb302.*;
import static project.honey.personDepart.entity.QTb201.*;

public class Tb302RepositoryDslImpl implements Tb302RepositoryDsl {

    private final JPAQueryFactory queryFactory;

    public Tb302RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tb302ResultDto> findAllResultByDsl(Pageable pageable) {


        return null;
    }
}
