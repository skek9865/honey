package project.honey.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.dto.Tb409Dto;
import project.honey.business.entity.Tb409;
import project.honey.business.form.Tb409Form;
import project.honey.business.repository.Tb409Repository;
import project.honey.system.dto.CodeDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Service040110Impl implements Service040110 {

    private final Tb409Repository tb409Repository;

    @Override
    public Boolean insert(Tb409Form form) {
        Tb409 entity = Tb409Form.toTb409(form);
        tb409Repository.save(entity);
        return true;
    }

    @Override
    public Page<Tb409Dto> findAllByDsl(Pageable pageable) {
        Page<Tb409> result = tb409Repository.findAll(pageable);
        Page<Tb409Dto> resultList = result.map(Tb409Dto::of);
        return resultList;
    }

    @Override
    public List<List<String>> findAllByExcel() {
        List<List<String>> resultList = new ArrayList<>();
        List<Tb409> result = tb409Repository.findAll();
        result.forEach(e -> {
            List<String> list = new ArrayList<>();
            list.add(e.getExcgCd());
            list.add(e.getExcgNm());
            list.add(String.valueOf(e.getExcgRate()));
            list.add(e.getUseYn());

            resultList.add(list);
        });
        return resultList;
    }

    @Override
    public Tb409Dto findById(Integer id) {
        Tb409 entity = tb409Repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb409Dto.of(entity);
    }

    @Transactional
    @Override
    public Boolean update(Tb409Form form) {
        Tb409 entity = tb409Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);
        entity.updateData(form);
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        tb409Repository.deleteById(id);
        return true;
    }

    @Override
    public List<CodeDto> findAllBySelect() {
        List<CodeDto> result = tb409Repository.findAllBySelect();
        return result;
    }
}
