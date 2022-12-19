package project.honey.company.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import project.honey.company.dto.QTb105Dto;
import project.honey.company.dto.Tb105Dto;
import project.honey.company.entity.Tb105;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static project.honey.company.entity.QTb105.tb105;
import static project.honey.company.entity.QTb105_1.tb105_1;

@SpringBootTest
class Service010204ImplTest {
    @Autowired
    Service010204 service010204;

    JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager em;


    @BeforeEach
    public void injection() {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Test
    @DisplayName("대출관리 전부 불러오기")
    public void findAllTest() throws Exception {

        Page<Tb105Dto> all = service010204.findAll(PageRequest.of(0, 100));
        for (Tb105Dto dto : all) {
            System.out.println("dto = " + dto);
        }
    }
}