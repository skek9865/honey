package project.honey.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.dto.Tb408Dto;
import project.honey.business.entity.Tb408;
import project.honey.business.form.Tb408Form;
import project.honey.business.repository.Tb408Repository;
import project.honey.system.dto.CodeDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Service040109Impl implements Service040109{

    private final Tb408Repository tb408Repository;

    @Override
    public Boolean insert(Tb408Form form) {
        Tb408 entity = Tb408Form.toTb408(form);
        tb408Repository.save(entity);
        return true;
    }

    @Override
    public Page<Tb408Dto> findAllByDsl(Pageable pageable) {
        Page<Tb408> result = tb408Repository.findAll(pageable);
        Page<Tb408Dto> resultList = result.map(Tb408Dto::of);
        return resultList;
    }

    @Override
    public List<List<String>> findAllByExcel() {
        List<List<String>> resultList = new ArrayList<>();

        List<Tb408> result = tb408Repository.findAll();
        result.forEach(e -> {
            List<String> list = new ArrayList<>();
            list.add(e.getProjectCd());
            list.add(String.valueOf(e.getProjectAl()));
            list.add(e.getProjectNm());

            resultList.add(list);
        });

        return resultList;
    }

    @Override
    public Tb408Dto findById(Integer id) {
        Tb408 entity = tb408Repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb408Dto.of(entity);
    }

    @Transactional
    @Override
    public Boolean update(Tb408Form form) {
        Tb408 entity = tb408Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);
        entity.updateData(form);
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        tb408Repository.deleteById(id);
        return true;
    }

    @Override
    public List<CodeDto> findAllBySelect() {
        List<CodeDto> resultList = new ArrayList<>();
        List<Tb408> result = tb408Repository.findAll();
        result.forEach(e -> {
            CodeDto codeDto = CodeDto.builder()
                    .value(e.getProjectCd())
                    .text(e.getProjectNm())
                    .build();
            resultList.add(codeDto);
        });
        return resultList;
    }
}
