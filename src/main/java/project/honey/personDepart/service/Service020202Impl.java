package project.honey.personDepart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.UploadService;
import project.honey.personDepart.dto.Tb204Dto;
import project.honey.personDepart.entity.Tb204;
import project.honey.personDepart.form.Tb204Form;
import project.honey.personDepart.repository.Tb204Repository;
import project.honey.system.dto.CodeDto;
import project.honey.system.repository.Tb906Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class Service020202Impl implements Service020202{

    private final Tb204Repository tb204Repository;
    private final Tb906Repository tb906Repository;
    private final UploadService uploadService;

    @Override
    public Boolean insert(Tb204Form form) throws IOException {
        String saveNm = "";
        String outNm = "";

        if(!form.getFile().isEmpty()) {
            saveNm = uploadService.uploadFileUuid(form.getFile(), "C:/study/spring/team/honey/src/main/resources/static/files/corp");
            outNm = form.getFile().getOriginalFilename();
        }

        Tb204 tb204 = Tb204Form.toTb204(form, saveNm, outNm);
        tb204Repository.save(tb204);
        return true;
    }

    @Override
    public Page<Tb204Dto> findAll(String outFNm, String part, Pageable pageable) {
        List<Tb204Dto> dtoList = new ArrayList<>();

        List<CodeDto> tb906 = tb906Repository.findByFstIdByDsl("25");

        Page<Tb204> result = tb204Repository.findAllByDsl(outFNm, part, pageable);
        for(Tb204 entity : result.getContent()){
            Tb204Dto tb204Dto = makeDto(entity, tb906);
            dtoList.add(tb204Dto);
        }

        Page<Tb204Dto> resultList = new PageImpl<>(dtoList, pageable, dtoList.size());

        return resultList;
    }

    @Override
    public List<Tb204Dto> findAllByExcel(String outFNm, String part) {
        List<Tb204Dto> dtoList = new ArrayList<>();

        List<CodeDto> tb906 = tb906Repository.findByFstIdByDsl("25");

        List<Tb204> result = tb204Repository.findAllByExcel(outFNm, part);

        log.info("outFNm = {}", outFNm);
        log.info("part = {}", part);

        for(Tb204 entity : result){
            Tb204Dto tb204Dto = makeDto(entity, tb906);
            dtoList.add(tb204Dto);
        }

        log.info("dtoList = {}",dtoList);

        return dtoList;
    }

    @Override
    public Tb204Dto findById(Integer id) {
        Tb204 entity = tb204Repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb204Dto.of(entity, null);
    }

    @Transactional
    @Override
    public Boolean update(Tb204Form form) {
        Tb204 entity = tb204Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);

        String saveNm = entity.getSaveFNm();
        String outNm = entity.getOutFNm();

        if(!form.getFile().isEmpty()) {
            uploadService.deleteFileUuid("C:/study/spring/team/honey/src/main/resources/static/files/corp", saveNm);
            saveNm = uploadService.uploadFileUuid(form.getFile(), "C:/study/spring/team/honey/src/main/resources/static/files/corp");
            outNm = form.getFile().getOriginalFilename();
        }

        entity.updateData(form, saveNm, outNm);
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        Tb204 entity = tb204Repository.findById(id).orElseThrow(RuntimeException::new);
        tb204Repository.deleteById(id);
        uploadService.deleteFileUuid("C:/study/spring/team/honey/src/main/resources/static/files/corp", entity.getSaveFNm());
        return true;
    }

    private Tb204Dto makeDto(Tb204 entity, List<CodeDto> tb906){
        String partNm = "";

        Optional<CodeDto> find906 = tb906.stream().filter(e -> e.getValue().equals(entity.getPart())).findAny();
        if(find906.isPresent()) partNm = find906.get().getText();
        else partNm = "";

        return Tb204Dto.of(entity, partNm);
    }
}
