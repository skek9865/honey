package project.honey.system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.pay.dto.Tb301Dto;
import project.honey.system.dto.Tb901Dto;
import project.honey.system.entity.Tb901;
import project.honey.system.repository.Tb901Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class Service990101Impl implements Service990101 {

    private final Tb901Repository tb901Repository;

    // User 저장
    @Override
    @Transactional
    public String insert(Tb901Dto dto) {
        return tb901Repository.save(Tb901Dto.toTb901(dto)).getUserId();
    }

    // User 수정
    @Override
    @Transactional
    public String update(Tb901Dto dto) {
        Tb901 user = tb901Repository.findById(dto.getUserId()).orElseThrow(RuntimeException::new);
        user.changeInfo(dto);
        return user.getUserId();
    }

    // User 리스트 불러오기
    @Override
    public Page<Tb901Dto> findAll(Pageable pageable) {
        return tb901Repository.findAllByDsl(pageable);
    }

    // User 단건 조회
    @Override
    public Tb901Dto findById(String userId) {
        return Tb901Dto.of(tb901Repository.findById(userId).orElseThrow(RuntimeException::new));
    }

    // 사용자 삭제
    @Override
    @Transactional
    public String delete(String userId) {
        tb901Repository.deleteById(userId);
        return userId;
    }

    @Override
    public Map<String, String> userList() {
        return tb901Repository.findAll().stream()
                .collect(Collectors.toMap(Tb901::getUserId, Tb901::getUserNm));
    }

    @Override
    public List<List<String>> findAllByExcel() {
        List<Tb901Dto> tb901s = tb901Repository.findAllByExcel();
        List<List<String>> excelData = new ArrayList<>();
        for(Tb901Dto dto : tb901s){
            List<String> list = new ArrayList<>();
            list.add(dto.getUserId());
            list.add(dto.getPasswd());
            list.add(dto.getUserNm());
            list.add(dto.getPhone());
            list.add(dto.getMobile());
            list.add(dto.getEmail());
            list.add(dto.getUserGr());
            list.add(dto.getUseYn());
            list.add(dto.getEmpYn());
            list.add(dto.getEmpNo());
            list.add(dto.getRegDt());
            excelData.add(list);
        }
        return excelData;
    }
}
