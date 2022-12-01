package project.honey.personDepart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.UploadService;
import project.honey.personDepart.dto.Tb203Dto;
import project.honey.personDepart.entity.Tb201;
import project.honey.personDepart.entity.Tb203;
import project.honey.personDepart.form.Tb203Form;
import project.honey.personDepart.repository.Tb201Repository;
import project.honey.personDepart.repository.Tb203Repository;
import project.honey.system.dto.CodeDto;
import project.honey.system.repository.Tb906Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Service020201Impl implements Service020201{

    private final Tb203Repository tb203Repository;
    private final Tb201Repository tb201Repository;
    private final Tb906Repository tb906Repository;
    private final UploadService uploadService;

    @Transactional
    @Override
    public Boolean insert(Tb203Form form) throws IOException {
        String saveNm = "";
        String outNm = "";

        if(!form.getFile().isEmpty()) {
            saveNm = uploadService.uploadFileUuid(form.getFile(), "C:/study/spring/team/honey/src/main/resources/static/files/emp");
            outNm = form.getFile().getOriginalFilename();
        }

        Tb203 entity = Tb203Form.toTb203(form, saveNm, outNm);
        tb203Repository.save(entity);
        return true;
    }

    @Override
    public Page<Tb203Dto> findAll(String outFNm, String part, Pageable pageable) {
        List<Tb203Dto> dtoList = new ArrayList<>();

        List<Tb201> tb201 = tb201Repository.findAll();
        List<CodeDto> tb906 = tb906Repository.findByFstIdByDsl("25");

        Page<Tb203> result = tb203Repository.findAllByDsl(outFNm, part, pageable);
        for(Tb203 entity : result.getContent()){
            Tb203Dto tb203Dto = makeDto(entity, tb201, tb906);
            dtoList.add(tb203Dto);
        }

        Page<Tb203Dto> resultList = new PageImpl<>(dtoList, pageable, dtoList.size());

        return resultList;
    }

    @Override
    public List<List<String>> findAllByExcel(String outFNm, String part) {
        List<List<String>> dtoList = new ArrayList<>();

        List<Tb201> tb201 = tb201Repository.findAll();
        List<CodeDto> tb906 = tb906Repository.findByFstIdByDsl("25");

        List<Tb203> result = tb203Repository.findAllByExcel(outFNm, part);

        String empNm;
        String partNm;
        for(Tb203 entity : result){
            List<String> list = new ArrayList<>();

            Optional<Tb201> find201 = tb201.stream().filter(e -> e.getEmpNo().equals(entity.getEmpNo())).findAny();
            if(find201.isPresent()) empNm = find201.get().getEmpNm();
            else empNm = "";

            Optional<CodeDto> find906 = tb906.stream().filter(e -> e.getValue().equals(entity.getPart())).findAny();
            if(find906.isPresent()) partNm = find906.get().getText();
            else partNm = "";

            list.add(empNm);
            list.add(partNm);
            list.add(entity.getOutFNm());

            dtoList.add(list);
        }

        return dtoList;
    }

    @Override
    public Tb203Dto findById(Integer id) {
        Tb203 entity = tb203Repository.findById(id).orElseThrow(RuntimeException::new);

        String empNm = "";

        List<Tb201> tb201 = tb201Repository.findAll();
        Optional<Tb201> find201 = tb201.stream().filter(e -> e.getEmpNo().equals(entity.getEmpNo())).findAny();
        if(find201.isPresent()) empNm = find201.get().getEmpNm();
        else empNm = "";

        return Tb203Dto.of(entity, empNm,null);
    }

    @Transactional
    @Override
    public Boolean update(Tb203Form form) {
        Tb203 entity = tb203Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);

        String saveNm = entity.getSaveFNm();
        String outNm = entity.getOutFNm();

        if(!form.getFile().isEmpty()) {
            uploadService.deleteFileUuid("C:/study/spring/team/honey/src/main/resources/static/files/emp", saveNm);
            saveNm = uploadService.uploadFileUuid(form.getFile(), "C:/study/spring/team/honey/src/main/resources/static/files/emp");
            outNm = form.getFile().getOriginalFilename();
        }

        entity.updateData(form, saveNm, outNm);
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        Tb203 entity = tb203Repository.findById(id).orElseThrow(RuntimeException::new);
        tb203Repository.deleteById(id);
        uploadService.deleteFileUuid("C:/study/spring/team/honey/src/main/resources/static/files/emp", entity.getSaveFNm());
        return true;
    }

    private Tb203Dto makeDto(Tb203 entity, List<Tb201> tb201, List<CodeDto> tb906){
        String empNm;
        String partNm;

        Optional<Tb201> find201 = tb201.stream().filter(e -> e.getEmpNo().equals(entity.getEmpNo())).findAny();
        if(find201.isPresent()) empNm = find201.get().getEmpNm();
        else empNm = "";

        Optional<CodeDto> find906 = tb906.stream().filter(e -> e.getValue().equals(entity.getPart())).findAny();
        if(find906.isPresent()) partNm = find906.get().getText();
        else partNm = "";

        return Tb203Dto.of(entity, empNm, partNm);
    }
}
