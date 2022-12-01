package project.honey.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.dto.Tb401Dto;
import project.honey.business.entity.Tb401;
import project.honey.business.form.Tb401Form;
import project.honey.business.repository.Tb401Repository;
import project.honey.system.dto.CodeDto;
import project.honey.system.repository.Tb906Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class Service040101Impl implements Service040101{

    private final Tb401Repository tb401Repository;
    private final Tb906Repository tb906Repository;

    @Override
    public Boolean insert(Tb401Form form) {
        Tb401 entity = Tb401Form.toTb401(form);
        tb401Repository.save(entity);
        return true;
    }

    @Override
    public Page<Tb401Dto> findAll(Pageable pageable) {
        List<Tb401Dto> dtoList = new ArrayList<>();

        List<CodeDto> tb906 = tb906Repository.findByFstIdByDsl("08");

        Page<Tb401> result = tb401Repository.findAll(pageable);

        for(Tb401 entity : result.getContent()){
            Tb401Dto tb401Dto = makeDto(entity, tb906);
            dtoList.add(tb401Dto);
        }

        Page<Tb401Dto> resultList = new PageImpl<>(dtoList, pageable, dtoList.size());

        return resultList;
    }

    @Override
    public List<Tb401Dto> findAllByExcel() {
        List<Tb401Dto> dtoList = new ArrayList<>();

        List<CodeDto> tb906 = tb906Repository.findByFstIdByDsl("08");

        List<Tb401> result = tb401Repository.findAll();

        for(Tb401 entity : result){
            Tb401Dto tb401Dto = makeDto(entity, tb906);
            dtoList.add(tb401Dto);
        }
        return dtoList;
    }

    @Override
    public Tb401Dto findById(Integer id) {
        Tb401 entity = tb401Repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb401Dto.of(entity, null);
    }

    @Transactional
    @Override
    public Boolean update(Tb401Form form) {
        Tb401 entity = tb401Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);
        entity.updateData(form);
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        tb401Repository.deleteById(id);
        return true;
    }

    private Tb401Dto makeDto(Tb401 entity, List<CodeDto> tb906){
        String classSeqNm = "";

        Optional<CodeDto> find906 = tb906.stream().filter(e -> e.getValue().equals(entity.getClassSeq())).findAny();
        if(find906.isPresent()) classSeqNm = find906.get().getText();
        else classSeqNm = "";

        return Tb401Dto.of(entity, classSeqNm);
    }
}
