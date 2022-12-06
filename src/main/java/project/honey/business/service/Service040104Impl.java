package project.honey.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.dto.Tb401Dto;
import project.honey.business.dto.Tb404Dto;
import project.honey.business.entity.Tb401;
import project.honey.business.entity.Tb404;
import project.honey.business.form.Tb401Form;
import project.honey.business.form.Tb404Form;
import project.honey.business.repository.Tb401Repository;
import project.honey.business.repository.Tb404Repository;
import project.honey.comm.CodeToName;
import project.honey.system.dto.CodeDto;
import project.honey.system.entity.Tb904;
import project.honey.system.repository.Tb906Repository;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class Service040104Impl implements Service040104{

    private final Tb404Repository tb404Repository;
    private final CodeToName codeToName;

    @Override
    @Transactional
    public Boolean insert(Tb404Form form) {
        Tb404 entity = Tb404Form.toTb404(form);
        tb404Repository.save(entity);
        return true;
    }

    @Override
    public Page<Tb404Dto> findAll(Pageable pageable) {
        Map<String, String> map = codeToName.commonCode("07");
        return  tb404Repository.findAll(pageable)
                .map(m -> Tb404Dto.of(m, map));
    }

    @Override
    public List<List<String>> findAllByExcel() {
        Map<String, String> map = codeToName.commonCode("07");
        List<Tb404> result = tb404Repository.findAll();

        List<List<String>> dtoList = new ArrayList<>();
        for(Tb404 entity : result){
            List<String> list = new ArrayList<>();
            list.add(map.get(entity.getClassSeq()));
            list.add(entity.getClassCd());
            list.add(String.valueOf(entity.getClassAl()));
            list.add(entity.getClassNm());
            dtoList.add(list);
        }
        return dtoList;
    }

    @Override
    public Tb404Dto findById(Integer id) {
        Map<String, String> map = codeToName.commonCode("07");
        Tb404 entity = tb404Repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb404Dto.of(entity, map);
    }

    @Transactional
    @Override
    public Boolean update(Tb404Form form) {
        Tb404 entity = tb404Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);
        entity.updateData(form);
        return true;
    }

    @Override
    @Transactional
    public Boolean delete(Integer id) {
        tb404Repository.deleteById(id);
        return true;
    }

    @Override
    public Map<String, String> findAllBySelect() {
        return tb404Repository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        Tb404::getClassCd,Tb404::getClassNm,
                        (key1, key2) -> key1,
                        LinkedHashMap::new));
    }
}
