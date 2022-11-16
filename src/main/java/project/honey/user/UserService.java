package project.honey.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    // 사용자 등록
    String insertUser(UserDto dto);

    // 사용자 수정
    String updateUser(UserDto dto);

    // 사용자 리스트 조회
    Page<UserDto> userList(Pageable pageable);

    // 사용자 삭제
    void deleteUser(String userId);
}
