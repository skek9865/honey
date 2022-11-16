package project.honey.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.honey.user.entity.Tb901;
import project.honey.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    // User 저장 및 수정
    @Override
    public String insertUser(UserDto dto) {
        return userRepository.save(UserDto.toUser(dto)).getUserId();
    }

    // User 저장 및 수정
    @Override
    public String updateUser(UserDto dto) {
        Tb901 entity = UserDto.toUser(dto);
        return userRepository.save(entity).getUserId();
    }

    // User 리스트 불러오기
    @Override
    public Page<UserDto> userList(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDto::of);
    }

    // 사용자 삭제
    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
