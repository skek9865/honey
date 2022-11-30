package project.honey.personDepart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.UploadService;
import project.honey.personDepart.entity.Tb202;
import project.honey.personDepart.form.Tb201Form;
import project.honey.personDepart.dto.Tb201Dto;
import project.honey.personDepart.entity.Tb201;
import project.honey.personDepart.repository.Tb201Repository;
import project.honey.personDepart.repository.Tb202Repository;
import project.honey.system.dto.CodeDto;
import project.honey.system.repository.Tb906Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class Service020101Impl implements Service020101 {

    private final Tb201Repository tb201Repository;
    private final Tb202Repository tb202Repository;
    private final Tb906Repository tb906Repository;
    private final UploadService uploadService;

    @Override
    public Boolean insert(Tb201Form form) throws IOException {
        String fileNm = form.getFile().getOriginalFilename();
        String imgNm = form.getImg().getOriginalFilename();

        Tb201 entity = Tb201Form.toTb201(form, fileNm, imgNm);
        Tb201 saveEntity = tb201Repository.save(entity);

        if(!form.getFile().isEmpty()) {
            uploadService.uploadFile(form.getFile(), "C:/study/spring/team/honey/src/main/resources/static/files/emp", saveEntity.getSeq());
        }
        if(!form.getImg().isEmpty()){
            uploadService.uploadFile(form.getImg(), "C:/study/spring/team/honey/src/main/resources/static/images/emp", saveEntity.getSeq());
        }

        return true;
    }

    @Override
    public Page<Tb201Dto> findAll(String empNm, String postCd, String deptCd, Pageable pageable) {
        List<Tb201Dto> dtoList = new ArrayList<>();

        List<Tb202> tb202 = tb202Repository.findAll();
        List<CodeDto> tb906 = tb906Repository.findByFstIdByDsl("01");

        Page<Tb201> result = tb201Repository.findAllByDsl(empNm, postCd, deptCd, pageable);
        for(Tb201 entity : result.getContent()){
            Tb201Dto tb201Dto = makeDto(entity, tb202, tb906);
            dtoList.add(tb201Dto);
        }

        Page<Tb201Dto> resultList = new PageImpl<>(dtoList, pageable, dtoList.size());

        return resultList;
    }

    @Override
    public List<Tb201Dto> findAllByExcel(String empNm, String postCd, String deptCd) {
        List<Tb201Dto> dtoList = new ArrayList<>();

        List<Tb202> tb202 = tb202Repository.findAll();
        List<CodeDto> tb906 = tb906Repository.findByFstIdByDsl("01");

        List<Tb201> result = tb201Repository.findAllByExcel(empNm, postCd, deptCd);

        for(Tb201 entity : result){
            Tb201Dto tb201Dto = makeDto(entity, tb202, tb906);
            dtoList.add(tb201Dto);
        }

        return dtoList;
    }

    @Override
    public Tb201Dto findById(Integer id) {
        Tb201 entity = tb201Repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb201Dto.of(entity,null,null);
    }

    @Transactional
    @Override
    public Boolean update(Tb201Form form) {
        Tb201 entity = tb201Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);

        String fileNm = entity.getFileNm();
        String imgNm = entity.getPicNm();

        if(!form.getFile().isEmpty()) {
            uploadService.deleteFile("C:/study/spring/team/honey/src/main/resources/static/files/emp", fileNm, entity.getSeq());
            fileNm = uploadService.uploadFile(form.getFile(), "C:/study/spring/team/honey/src/main/resources/static/files/emp", entity.getSeq());
        }
        if(!form.getImg().isEmpty()){
            uploadService.deleteFile("C:/study/spring/team/honey/src/main/resources/static/images/emp", imgNm, entity.getSeq());
            imgNm = uploadService.uploadFile(form.getImg(), "C:/study/spring/team/honey/src/main/resources/static/images/emp", entity.getSeq());
        }

        entity.updateData(form, fileNm, imgNm);
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        Tb201 entity = tb201Repository.findById(id).orElseThrow(RuntimeException::new);
        tb201Repository.deleteById(id);
        uploadService.deleteFile("C:/study/spring/team/honey/src/main/resources/static/files/emp", entity.getFileNm(), id);
        uploadService.deleteFile("C:/study/spring/team/honey/src/main/resources/static/images/emp", entity.getPicNm(), id);
        return true;
    }

    @Override
    public Tb201Dto findByEmpNo(String empNo) {
        Tb201 entity = tb201Repository.findByEmpNo(empNo).orElseThrow(RuntimeException::new);
        return Tb201Dto.of(entity,null,null);
    }

    private Tb201Dto makeDto(Tb201 entity, List<Tb202> tb202, List<CodeDto> tb906){
        String deptNm = "";
        String postNm = "";

        Optional<Tb202> find202 = tb202.stream().filter(e -> e.getDeptCd().equals(entity.getDeptCd())).findAny();
        if(find202.isPresent()) deptNm = find202.get().getDeptNm();
        else deptNm = "";
        Optional<CodeDto> find906 = tb906.stream().filter(e -> e.getValue().equals(entity.getPost())).findAny();
        if(find906.isPresent()) postNm = find906.get().getText();
        else postNm = "";

        return Tb201Dto.of(entity, postNm, deptNm);
    }
}
