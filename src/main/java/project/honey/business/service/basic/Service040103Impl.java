package project.honey.business.service.basic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.dto.basic.Tb403Dto;
import project.honey.business.entity.basic.Tb403;
import project.honey.business.form.basic.Tb403Form;
import project.honey.business.repository.basic.Tb403Repository;
import project.honey.system.dto.CodeDto;
import project.honey.system.repository.Tb906Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Service040103Impl implements Service040103{

    private final Tb403Repository tb403Repository;
    private final Tb906Repository tb906Repository;

    @Override
    public Boolean insert(Tb403Form form) {
        Tb403 entity = Tb403Form.toTb403(form);
        tb403Repository.save(entity);
        return true;
    }

    @Override
    public Page<Tb403Dto> findAllByDsl(Pageable pageable) {
        String whouseClaNm;
        List<Tb403Dto> dtoList = new ArrayList<>();

        List<CodeDto> tb906 = tb906Repository.findByFstIdByDsl("06");

        Page<Tb403> result = tb403Repository.findAll(pageable);

        for(Tb403 entity : result.getContent()){
            whouseClaNm = makeWhouseClaNm(entity, tb906);
            Tb403Dto tb403Dto = Tb403Dto.of(entity, whouseClaNm);
            dtoList.add(tb403Dto);
        }

        Page<Tb403Dto> resultList = new PageImpl<>(dtoList, pageable, result.getTotalElements());

        return resultList;
    }

    @Override
    public List<List<String>> findAllByExcel() {
        List<List<String>> dtoList = new ArrayList<>();

        List<CodeDto> tb906 = tb906Repository.findByFstIdByDsl("06");

        List<Tb403> result = tb403Repository.findAll();

        for (Tb403 entity : result){
            List<String> list = new ArrayList<>();
            String whouseClaNm = makeWhouseClaNm(entity, tb906);
            list.add(whouseClaNm);
            list.add(entity.getProductCd());
            list.add(entity.getWhouseCd());
            list.add(String.valueOf(entity.getWhouseAl()));
            list.add(entity.getWhouseNm());

            dtoList.add(list);
        }
        return dtoList;
    }

    @Override
    public Tb403Dto findById(Integer id) {
        Tb403 entity = tb403Repository.findById(id).orElseThrow(RuntimeException::new);
        Tb403Dto dto = Tb403Dto.of(entity, null);
        return dto;
    }

    @Transactional
    @Override
    public Boolean update(Tb403Form form) {
        Tb403 entity = tb403Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);
        entity.updateData(form);
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        tb403Repository.deleteById(id);
        return true;
    }

    @Override
    public List<CodeDto> findAllBySelect() {
        List<CodeDto> resultList = new ArrayList<>();
        List<Tb403> result = tb403Repository.findAll();
        result.forEach(e -> {
            CodeDto dto = CodeDto.builder()
                    .value(e.getWhouseCd())
                    .text(e.getWhouseNm())
                    .build();
            resultList.add(dto);
        });
        return resultList;
    }

    private String makeWhouseClaNm(Tb403 entity, List<CodeDto> tb906){
        String whouseClaNm;

        Optional<CodeDto> find906 = tb906.stream().filter(e -> e.getValue().equals(entity.getWhouseCla())).findAny();
        if(find906.isPresent()) whouseClaNm = find906.get().getText();
        else whouseClaNm = "";

        return whouseClaNm;
    }
}
