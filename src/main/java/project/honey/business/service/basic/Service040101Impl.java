package project.honey.business.service.basic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.dto.basic.Tb401Dto;
import project.honey.business.entity.basic.Tb401;
import project.honey.business.form.basic.Tb401Form;
import project.honey.business.repository.basic.Tb401Repository;
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
        String classSeqNm;
        List<Tb401Dto> dtoList = new ArrayList<>();

        List<CodeDto> tb906 = tb906Repository.findByFstIdByDsl("08");

        Page<Tb401> result = tb401Repository.findAll(pageable);

        for(Tb401 entity : result.getContent()){
            classSeqNm = makeClassSeqNm(entity, tb906);
            Tb401Dto tb401Dto = Tb401Dto.of(entity,classSeqNm);
            dtoList.add(tb401Dto);
        }

        Page<Tb401Dto> resultList = new PageImpl<>(dtoList, pageable, result.getTotalElements());

        return resultList;
    }

    @Override
    public List<List<String>> findAllByExcel() {
        String classSeqNm;
        List<List<String>> dtoList = new ArrayList<>();

        List<CodeDto> tb906 = tb906Repository.findByFstIdByDsl("08");

        List<Tb401> result = tb401Repository.findAll();

        for(Tb401 entity : result){
            List<String> list = new ArrayList<>();

            classSeqNm = makeClassSeqNm(entity, tb906);

            list.add(classSeqNm);
            list.add(entity.getClassCd());
            list.add(String.valueOf(entity.getClassAl()));
            list.add(entity.getClassNm());

            dtoList.add(list);
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

    @Override
    public List<CodeDto> findAllBySelect(Integer type) {
        List<CodeDto> result = tb401Repository.findAllBySelect(type);
        return result;
    }

    private String makeClassSeqNm(Tb401 entity, List<CodeDto> tb906){
        String classSeqNm;

        Optional<CodeDto> find906 = tb906.stream().filter(e -> e.getValue().equals(entity.getClassSeq())).findAny();
        if(find906.isPresent()) classSeqNm = find906.get().getText();
        else classSeqNm = "";

        return classSeqNm;
    }
}
