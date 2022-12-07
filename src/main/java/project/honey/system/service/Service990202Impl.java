package project.honey.system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.CodeToName;
import project.honey.system.dto.Tb905Dto;
import project.honey.system.entity.Tb904;
import project.honey.system.entity.Tb905;
import project.honey.system.repository.Tb904Repository;
import project.honey.system.repository.Tb905Repository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class Service990202Impl implements Service990202{

    private final Tb905Repository tb905Repository;
    private final Tb904Repository tb904Repository;
    private final CodeToName codeToName;

    @Override
    @Transactional
    public Integer insert(Tb905Dto dto) {
        Tb904 tb904 = tb904Repository.findById(Integer.valueOf(dto.getTpId())).orElseThrow(EntityNotFoundException::new);
        return tb905Repository.save(Tb905Dto.toTb905(dto, tb904)).getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb905Dto dto) {
        Tb904 tb904 = tb904Repository.findById(Integer.valueOf(dto.getTpId())).orElseThrow(EntityNotFoundException::new);
        Tb905 tb905 = tb905Repository.findById(dto.getSeq()).orElseThrow(EntityNotFoundException::new);
        tb905.changeInfo(dto, tb904);
        return tb905.getSeq();
    }

    @Override
    public Page<Tb905Dto> findAll(Pageable pageable) {
        Map<String, String> screenMap = codeToName.screen();
        Map<String, String> menuGbMap = codeToName.commonCode("99");
        return tb905Repository.findAllByDsl(pageable)
                .map(m -> Tb905Dto.of(m, menuGbMap, screenMap));
    }

    @Override
    public Tb905Dto findById(Integer id) {
        Map<String, String> screenMap = codeToName.screen();
        Map<String, String> menuGbMap = codeToName.commonCode("99");
        return Tb905Dto.of(tb905Repository.findById(id).orElseThrow(EntityNotFoundException::new),menuGbMap, screenMap);
    }

    @Override
    @Transactional
    public Integer delete(Integer seq) {
        tb905Repository.deleteById(seq);
        return seq;
    }

    @Override
    public List<List<String>> findAllByExcel() {
        List<Tb905> tb905s = tb905Repository.findAllByExcel();
        List<List<String>> excelData = new ArrayList<>();
        Map<String, String> screenMap = codeToName.screen();
        Map<String, String> menuGbMap = codeToName.commonCode("99");

        for(Tb905 entity : tb905s){
            List<String> list = new ArrayList<>();
            list.add(menuGbMap.get(entity.getMenuGbCd()));
            list.add(screenMap.get(entity.getTpId()));
            list.add(entity.getMenuYn());
            list.add(entity.getListYn());
            list.add(entity.getViewYn());
            list.add(entity.getSaveYn());
            list.add(entity.getModifyYn());
            list.add(entity.getDelYn());
            excelData.add(list);
        }
        return excelData;
    }
}
