package project.honey.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    // 사용자 등록
    String insert(UserDto dto);

    // 사용자 수정
    String update(UserDto dto);

    // 사용자 리스트 조회
    Page<UserDto> findAll(Pageable pageable);

    // 사용자 단건 조회
    UserDto findById(String userId);

    // 사용자 삭제
    void delete(String userId);
}
