package project.honey.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import project.honey.system.dto.Tb901Dto;
import project.honey.system.service.Service990101;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@WebAppConfiguration
class Service990101ImplTest {

    @Autowired
    private Service990101 service990101;

    @Qualifier("httpSession")
    @Autowired
    private HttpSession session;

    @PostConstruct
    private void insertUser() {
        session.setAttribute("user", "hello");

        IntStream.range(0, 2000).forEach(i -> {
            Tb901Dto dto = Tb901Dto.builder()
                    .userId("test" + i)
                    .passwd("test" + i)
                    .userNm("tester" + i)
                    .phone("test" + i)
                    .mobile("test" + i)
                    .email("test" + i)
                    .userGr("t" + i)
                    .useYn(i % 2 == 0 ? "Y" : "N")
                    .empYn(i % 2 == 0 ? "Y" : "N")
                    .empNo("test" + i)
                    .regDt("test" + i)
                    .build();

            service990101.insert(dto);
        });
    }

    // insert 메서드


    @Test
    void insert() {

        insertUser();

        Tb901Dto findUser = service990101.findById("test50");

        assertThat(findUser.getUserId()).isEqualTo("test50");

    }

    @Test
    void update() {

        // given
        session.invalidate();
        session.setAttribute("user", "hello2");

        Tb901Dto dto = service990101.findById("test50");
        dto.setEmail("hello~~~");

        //when
        String userId = service990101.update(dto);
        Tb901Dto findUser = service990101.findById(userId);
        //then
        assertThat(findUser.getUserId()).isEqualTo(userId);
    }

    @Test
    @Rollback
    void find() {
        insertUser();
        Page<Tb901Dto> pagingUsers = service990101.findAll(PageRequest.of(1, 50));

        assertThat(pagingUsers.getTotalElements()).isEqualTo(202);  // 총 사이즈 검증
        assertThat(pagingUsers.getContent().size()).isEqualTo(50);  // 페이징한 사이즈 검증

    }

    @Test
    @Rollback
    void delete() {
        String userId = "test100";
        service990101.delete(userId);
//        assertThatThrownBy(() -> userService.findById(userId))
//                .isInstanceOf(RuntimeException.class)
//                .hasMessageContaining("해당 회원이 없습니다.");
        assertThrows(RuntimeException.class, () -> {
            service990101.findById(userId);
        });
    }

}