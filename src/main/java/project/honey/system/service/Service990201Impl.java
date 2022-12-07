package project.honey.system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.system.dto.Tb904Dto;
import project.honey.system.entity.Tb904;
import project.honey.system.repository.Tb904Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class Service990201Impl implements Service990201{

    private final Tb904Repository tb904Repository;

    @Override
    @Transactional
    public Integer insert(Tb904Dto dto) {
        return tb904Repository.save(Tb904Dto.toTb904(dto)).getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb904Dto dto) {
        Tb904 tb904 = tb904Repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        tb904.changeInfo(dto);
        return tb904.getSeq();
    }

    @Override
    public Page<Tb904Dto> findAll(Pageable pageable) {
        return tb904Repository.findAllByDsl(pageable).map(Tb904Dto::of);
    }

    @Override
    public Tb904Dto findById(Integer seq) {
        return Tb904Dto.of(tb904Repository.findById(seq).orElseThrow(RuntimeException::new));
    }

    @Override
    @Transactional
    public Integer delete(Integer seq) {
        tb904Repository.deleteById(seq);
        return seq;
    }

    @Override
    public List<List<String>> findAllByExcel() {
        List<Tb904> tb904s = tb904Repository.findAllByExcel();
        List<List<String>> excelData = new ArrayList<>();
        for(Tb904 entity : tb904s){
            List<String> list = new ArrayList<>();
            list.add(entity.getFstId());
            list.add(entity.getScdId());
            list.add(entity.getThdId());
            list.add(String.valueOf(entity.getAlien()));
            list.add(entity.getMenuNm());
            list.add(entity.getMenuUrl());
            list.add(entity.getUseYn());
            excelData.add(list);
        }
        return excelData;
    }

    // 소그룹 구하기
    @Override
    public LinkedHashMap<Integer, String> findThdMenuAll() {

        return tb904Repository.findThdMenuAll()
                .stream()
                .collect(Collectors.toMap(
                        Tb904::getSeq,Tb904::getMenuNm,
                        (key1, key2) -> key1,
                        LinkedHashMap::new));
    }
}
