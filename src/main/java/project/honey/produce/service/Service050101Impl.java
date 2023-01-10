package project.honey.produce.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.produce.dto.Tb501Dto;
import project.honey.produce.entity.Tb501;
import project.honey.produce.repository.Tb501Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class Service050101Impl implements Service050101{

    private final Tb501Repository tb501Repository;

    @Override
    @Transactional
    public Integer insert(Tb501Dto dto) {

        return tb501Repository.save(Tb501Dto.toTb501(dto)).getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb501Dto dto) {
        Tb501 tb501 = tb501Repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        tb501.updateInfo(dto);
        return tb501.getSeq();
    }

    @Override
    public Page<Tb501Dto> findAll(Pageable pageable) {
        return tb501Repository.findAllByDsl(pageable).map(Tb501Dto::of);
    }

    @Override
    public Tb501Dto findById(Integer seq) {
        return Tb501Dto.of(tb501Repository.findById(seq).orElseThrow(RuntimeException::new));
    }

    @Override
    @Transactional
    public Integer delete(Integer seq) {
        tb501Repository.deleteById(seq);
        return seq;
    }

    @Override
    public List<List<String>> findAllByExcel() {
        List<Tb501> tb501s = tb501Repository.findAllByExcel();
        List<List<String>> excelData = new ArrayList<>();
        for(Tb501 entity : tb501s){
            List<String> list = new ArrayList<>();
            list.add(entity.getProductCd());
            list.add(String.valueOf(entity.getProductAl()));
            list.add(entity.getProductNm());
            excelData.add(list);
        }
        return excelData;
    }

    @Override
    public Map<String, String> findAllBySelect() {
        return tb501Repository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        Tb501::getProductCd,Tb501::getProductNm,
                        (key1, key2) -> key1,
                        LinkedHashMap::new));
    }
}
