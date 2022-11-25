package project.honey.personDepart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.personDepart.dto.Tb203Dto;
import project.honey.personDepart.entity.Tb203;
import project.honey.personDepart.form.Tb203Form;
import project.honey.personDepart.repository.Tb203Repository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Service020201Impl implements Service020201{

    private final Tb203Repository tb203Repository;

    @Transactional
    @Override
    public Boolean insert(Tb203Form form) throws IOException {
        String saveNm = "";
        String outNm = "";
        Tb203 tb203 = Tb203Form.toTb203(form, saveNm, outNm);
        tb203Repository.save(tb203);
        return true;
    }

    @Override
    public Page<Tb203Dto> findAll(String outFNm, String part, Pageable pageable) {
        Page<Tb203> result = tb203Repository.findAllByDsl(outFNm, part, pageable);
        Page<Tb203Dto> resultList = result.map(Tb203Dto::of);
        return resultList;
    }

    @Override
    public List<Tb203Dto> findAllByExcel(String outFNm, String part) {
        List<Tb203> result = tb203Repository.findAllByExcel(outFNm, part);
        List<Tb203Dto> resultList = result.stream().map(Tb203Dto::of).collect(Collectors.toList());
        return resultList;
    }

    @Override
    public Tb203Dto findById(Integer id) {
        Tb203 entity = tb203Repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb203Dto.of(entity);
    }

    @Transactional
    @Override
    public Boolean update(Tb203Form form) {
        Tb203 entity = tb203Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);
        String saveNm = "";
        String outNm = "";
//        if(form.getFile().isEmpty()) {
//            saveNm = entity.getSaveFNm();
//            outNm = entity.getOutFNm();
//        }
        entity.updateData(form, saveNm, outNm);
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        tb203Repository.deleteById(id);
        return true;
    }
}
