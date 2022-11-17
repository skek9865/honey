package project.honey.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.user.entity.Tb901;
import project.honey.user.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final HttpSession session;

    // User 저장
    @Override
    @Transactional
    public String insert(UserDto dto) {
        Tb901 entity = UserDto.toUser(dto);
        return userRepository.save(entity).getUserId();
    }

    // User 수정
    @Override
    @Transactional
    public String update(UserDto dto) {
        Tb901 user = userRepository.findById(dto.getUserId()).orElseThrow(RuntimeException::new);
        user.changeInfo(dto);
        return user.getUserId();
    }

    // User 리스트 불러오기
    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDto::of);
    }

    // User 단건 조회
    @Override
    public UserDto findById(String userId) {
        return UserDto.of(userRepository.findById(userId).orElseThrow(RuntimeException::new));
    }

    // 사용자 삭제
    @Override
    @Transactional
    public void delete(String userId) {
        userRepository.deleteById(userId);
    }
}
