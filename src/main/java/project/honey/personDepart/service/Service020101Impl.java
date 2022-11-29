package project.honey.personDepart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.UploadService;
import project.honey.personDepart.form.Tb201Form;
import project.honey.personDepart.dto.Tb201Dto;
import project.honey.personDepart.entity.Tb201;
import project.honey.personDepart.repository.Tb201Repository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class Service020101Impl implements Service020101 {

    private final Tb201Repository repository;
    private final UploadService uploadService;

    @Override
    public Boolean insert(Tb201Form form) throws IOException {
        String fileNm = form.getFile().getOriginalFilename();
        String imgNm = form.getImg().getOriginalFilename();

        Tb201 entity = Tb201Form.toTb201(form, fileNm, imgNm);
        Tb201 saveEntity = repository.save(entity);

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
        Page<Tb201> result = repository.findAllByDsl(empNm, postCd, deptCd, pageable);
        Page<Tb201Dto> resultList = result.map(Tb201Dto::of);
        return resultList;
    }

    @Override
    public List<Tb201Dto> findAllByExcel(String empNm, String postCd, String deptCd) {
        return repository.findAllByExcel(empNm, postCd, deptCd).stream().map(Tb201Dto::of).collect(Collectors.toList());
    }

    @Override
    public Tb201Dto findById(Integer id) {
        Tb201 entity = repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb201Dto.of(entity);
    }

    @Transactional
    @Override
    public Boolean update(Tb201Form form) {
        Tb201 entity = repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);

        String fileNm = entity.getFileNm();
        String imgNm = entity.getPicNm();

        if(!form.getFile().isEmpty()) {
            fileNm = uploadService.uploadFile(form.getFile(), "C:/study/spring/team/honey/src/main/resources/static/files/emp", entity.getSeq());
        }
        if(!form.getImg().isEmpty()){
            imgNm = uploadService.uploadFile(form.getImg(), "C:/study/spring/team/honey/src/main/resources/static/images/emp", entity.getSeq());
        }

        entity.updateData(form, fileNm, imgNm);
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        Tb201 entity = repository.findById(id).orElseThrow(RuntimeException::new);
        repository.deleteById(id);
        uploadService.deleteFile("C:/study/spring/team/honey/src/main/resources/static/files/emp", entity.getFileNm(), id);
        uploadService.deleteFile("C:/study/spring/team/honey/src/main/resources/static/images/emp", entity.getPicNm(), id);
        return true;
    }

    @Override
    public Tb201Dto findByEmpNo(String empNo) {
        Tb201 entity = repository.findByEmpNo(empNo).orElseThrow(RuntimeException::new);
        return Tb201Dto.of(entity);
    }
}
