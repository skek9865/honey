package project.honey.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.dto.Tb406Dto;
import project.honey.business.entity.Tb406;
import project.honey.business.form.Tb406Form;
import project.honey.business.repository.Tb406Repository;
import project.honey.system.dto.CodeDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Service040107Impl implements Service040107 {

    private final Tb406Repository tb406Repository;

    @Override
    public Boolean insert(Tb406Form form) {
        Tb406 entity = Tb406Form.toTb406(form);
        tb406Repository.save(entity);
        return true;
    }

    @Override
    public Page<Tb406Dto> findAllByDsl(Pageable pageable) {
        Page<Tb406> result = tb406Repository.findAll(pageable);
        Page<Tb406Dto> resultList = result.map(Tb406Dto::of);
        return resultList;
    }

    @Override
    public List<List<String>> findAllByExcel() {
        List<List<String>> resultList = new ArrayList<>();
        List<Tb406> result = tb406Repository.findAll();

        result.forEach(e -> {
            List<String> list = new ArrayList<>();
            list.add(e.getSpecialCd());
            list.add(String.valueOf(e.getSpecialAl()));
            list.add(e.getSpecialNm());

            resultList.add(list);
        });
        return resultList;
    }

    @Override
    public Tb406Dto findById(Integer id) {
        Tb406 entity = tb406Repository.findById(id).orElseThrow(RuntimeException::new);
        Tb406Dto dto = Tb406Dto.of(entity);
        return dto;
    }

    @Transactional
    @Override
    public Boolean update(Tb406Form form) {
        Tb406 entity = tb406Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);
        entity.updateData(form);
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        tb406Repository.deleteById(id);
        return true;
    }

    @Override
    public List<CodeDto> findAllBySelect() {
        List<CodeDto> result = tb406Repository.findAllBySelect();
        return result;
    }
}
