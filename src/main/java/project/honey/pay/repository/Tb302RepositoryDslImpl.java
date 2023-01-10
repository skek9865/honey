package project.honey.pay.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.pay.dto.QTb302PopupDto;
import project.honey.pay.dto.Tb302HomeDto;
import project.honey.pay.dto.Tb302PopupDto;
import project.honey.pay.entity.QTb301;
import project.honey.pay.entity.QTb302;
import project.honey.personDepart.entity.QTb201;
import project.honey.system.entity.QTb906;

import javax.persistence.EntityManager;
import java.util.List;

import static project.honey.pay.entity.QTb301.*;
import static project.honey.pay.entity.QTb302.*;
import static project.honey.personDepart.entity.QTb201.*;
import static project.honey.system.entity.QTb906.*;

public class Tb302RepositoryDslImpl implements Tb302RepositoryDsl {

    private final JPAQueryFactory queryFactory;

    public Tb302RepositoryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


}
