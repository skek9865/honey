package project.honey.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.user.dto.Tb901Dto;
import project.honey.user.entity.Tb901;
import project.honey.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    // User 저장
    @Override
    @Transactional
    public String insert(Tb901Dto dto) {
        return userRepository.save(Tb901Dto.toTb901(dto)).getUserId();
    }

    // User 수정
    @Override
    @Transactional
    public String update(Tb901Dto dto) {
        Tb901 user = userRepository.findById(dto.getUserId()).orElseThrow(RuntimeException::new);
        user.changeInfo(dto);
        return user.getUserId();
    }

    // User 리스트 불러오기
    @Override
    public Page<Tb901Dto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(Tb901Dto::of);
    }

    // User 단건 조회
    @Override
    public Tb901Dto findById(String userId) {
        return Tb901Dto.of(userRepository.findById(userId).orElseThrow(RuntimeException::new));
    }

    // 사용자 삭제
    @Override
    @Transactional
    public void delete(String userId) {
        userRepository.deleteById(userId);
    }
}
