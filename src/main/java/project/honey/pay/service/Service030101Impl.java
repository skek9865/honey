package project.honey.pay.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.pay.dto.Tb301Dto;
import project.honey.pay.entity.Tb301;
import project.honey.pay.repository.Tb301Repository;
import project.honey.personDepart.entity.Tb202;
import project.honey.system.dto.CodeDto;
import project.honey.system.entity.Tb906;
import project.honey.system.repository.Tb906Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service030101Impl implements Service030101{

    private final Tb301Repository tb301Repository;

    @Override
    @Transactional
    public Integer insert(Tb301Dto dto) {
        return tb301Repository.save(Tb301Dto.toTb301(dto)).getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb301Dto dto) {
        Tb301 pay = tb301Repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        pay.changeInfo(dto);
        return pay.getSeq();
    }

    @Override
    public Page<Tb301Dto> findAll(Pageable pageable) {
        return tb301Repository.findAllByDsl(pageable);
    }

    @Override
    public Tb301Dto findById(Integer seq) {
        return Tb301Dto.of(tb301Repository.findById(seq).orElseThrow(RuntimeException::new));
    }

    @Override
    @Transactional
    public Integer delete(Integer seq) {
        tb301Repository.deleteById(seq);
        return seq;
    }

    @Override
    public List<CodeDto> findAllItem() {
        List<Tb301> findList = tb301Repository.findAll();
        return findList.stream()
                .filter(f -> f.getUseYn().equals("Y"))
                .map(entity -> (CodeDto.builder().text(entity.getItemNm()).value(entity.getItemCd()).build()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByUseItemNm() {
        return tb301Repository.findAllByUseItemNm();
    }

    // 엑셀용 데이터
    @Override
    public List<List<String>> findAllByExcel() {
        List<Tb301Dto> tb301s = tb301Repository.findAllByExcel();
        List<List<String>> excelData = new ArrayList<>();
        for(Tb301Dto dto : tb301s){
            List<String> list = new ArrayList<>();
            list.add(dto.getItemDiv());
            list.add(dto.getTaxDiv());
            list.add(dto.getItemCd());
            list.add(dto.getItemNm());
            list.add(String.valueOf(dto.getTaxRate()));
            list.add(dto.getUseYn());

            excelData.add(list);
        }
        return excelData;
    }


}
