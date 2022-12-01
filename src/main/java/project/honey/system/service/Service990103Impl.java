package project.honey.system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.CodeToName;
import project.honey.system.dto.Tb901Dto;
import project.honey.system.dto.Tb903Dto;
import project.honey.system.entity.Tb901;
import project.honey.system.entity.Tb903;
import project.honey.system.entity.Tb904;
import project.honey.system.repository.Tb903Repository;
import project.honey.system.repository.Tb904Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class Service990103Impl implements Service990103{

    private final Tb903Repository tb903Repository;
    private final Tb904Repository tb904Repository;
    private final CodeToName codeToName;

    @Override
    @Transactional
    public Integer insert(Tb903Dto dto) {
        Tb904 tb904 = tb904Repository.findById(Integer.parseInt(dto.getTpId()))
                .orElseThrow(RuntimeException::new);
        return tb903Repository.save(Tb903Dto.toTb903(dto, tb904)).getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb903Dto dto) {
        Tb903 tb903 = tb903Repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        Tb904 tb904 = tb904Repository.findById(Integer.parseInt(dto.getTpId()))
                .orElseThrow(RuntimeException::new);
        tb903.changeInfo(dto,tb904);
        return tb903.getSeq();
    }

    @Override
    public Page<Tb903Dto> findAll(Pageable pageable) {
        Map<String, String> screenMap = codeToName.screen();
        return tb903Repository.findAllByDsl(pageable)
                .map(m -> Tb903Dto.of(m, screenMap));
    }

    @Override
    public Tb903Dto findById(Integer seq) {
        return Tb903Dto.of(tb903Repository.findById(seq).orElseThrow(RuntimeException::new), codeToName.screen());
    }

    @Override
    @Transactional
    public Integer delete(Integer seq) {
        tb903Repository.deleteById(seq);
        return seq;
    }

    @Override
    public List<List<String>> findAllByExcel() {
        List<Tb903> tb903s = tb903Repository.findAllByExcel();
        List<List<String>> excelData = new ArrayList<>();
        Map<String, String> screenMap = codeToName.screen();

        for(Tb903 entity : tb903s){
            List<String> list = new ArrayList<>();
            list.add(entity.getUserId());
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
