package project.honey.system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.system.entity.Tb904;
import project.honey.system.repository.Tb904Repository;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class Service990201Impl implements Service990201{

    private final Tb904Repository tb904Repository;

    // 소그룹 구하기
    @Override
    public LinkedHashMap<Integer, String> findThdMenuAll() {

        return tb904Repository.findThdMenuAll()
                .stream()
                .filter(f -> f.getUseYn().equals("Y"))
                .collect(Collectors.toMap(
                        Tb904::getSeq,Tb904::getMenuNm,
                        (key1, key2) -> key1,
                        LinkedHashMap::new));
    }
}
