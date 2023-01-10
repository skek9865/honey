package project.honey.produce.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.produce.dto.Tb501Dto;
import project.honey.produce.dto.Tb502Dto;
import project.honey.produce.entity.Tb501;
import project.honey.produce.entity.Tb502;
import project.honey.produce.repository.Tb502Repository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class Service050102Impl implements Service050102{

    private final Tb502Repository tb502Repository;

    @Override
    @Transactional
    public Integer insert(Tb502Dto dto) {

        return tb502Repository.save(Tb502Dto.toTb502(dto)).getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb502Dto dto) {
        Tb502 tb502 = tb502Repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        tb502.updateInfo(dto);
        return tb502.getSeq();
    }

    @Override
    public Page<Tb502Dto> findAll(Pageable pageable) {
        return tb502Repository.findAllByDsl(pageable).map(Tb502Dto::of);
    }

    @Override
    public Tb502Dto findById(Integer seq) {
        return Tb502Dto.of(tb502Repository.findById(seq).orElseThrow(RuntimeException::new));
    }

    @Override
    @Transactional
    public Integer delete(Integer seq) {
        tb502Repository.deleteById(seq);
        return seq;
    }

    @Override
    public List<List<String>> findAllByExcel() {
        List<Tb502> tb502s = tb502Repository.findAllByExcel();
        List<List<String>> excelData = new ArrayList<>();
        for(Tb502 entity : tb502s){
            List<String> list = new ArrayList<>();
            list.add(entity.getFaulyCd());
            list.add(entity.getFaulyNm());
            list.add(entity.getNote());
            list.add(String.valueOf(entity.getErrorAl()));
            list.add(entity.getUseYn());
            excelData.add(list);
        }
        return excelData;
    }
}
