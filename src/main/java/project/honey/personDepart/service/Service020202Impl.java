package project.honey.personDepart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.personDepart.dto.Tb204Dto;
import project.honey.personDepart.entity.Tb204;
import project.honey.personDepart.form.Tb204Form;
import project.honey.personDepart.repository.Tb204Repository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Service020202Impl implements Service020202{

    private final Tb204Repository tb204Repository;

    @Override
    public Boolean insert(Tb204Form form) throws IOException {
        String saveNm = "";
        String outNm = "";
        Tb204 tb204 = Tb204Form.toTb204(form, saveNm, outNm);
        tb204Repository.save(tb204);
        return true;
    }

    @Override
    public Page<Tb204Dto> findAll(String outFNm, String part, Pageable pageable) {
        Page<Tb204> result = tb204Repository.findAllByDsl(outFNm, part, pageable);
        Page<Tb204Dto> resultList = result.map(Tb204Dto::of);
        return resultList;
    }

    @Override
    public List<Tb204Dto> findAllByExcel(String outFNm, String part) {
        List<Tb204> result = tb204Repository.findAllByExcel(outFNm, part);
        List<Tb204Dto> resultList = result.stream().map(Tb204Dto::of).collect(Collectors.toList());
        return resultList;
    }

    @Override
    public Tb204Dto findById(Integer id) {
        Tb204 entity = tb204Repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb204Dto.of(entity);
    }

    @Transactional
    @Override
    public Boolean update(Tb204Form form) {
        Tb204 entity = tb204Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);
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
        tb204Repository.deleteById(id);
        return true;
    }
}
