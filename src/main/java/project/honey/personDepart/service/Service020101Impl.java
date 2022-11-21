package project.honey.personDepart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.file.FileConverter;
import project.honey.comm.file.FileName;
import project.honey.personDepart.dto.Form020101;
import project.honey.personDepart.dto.Tb201Dto;
import project.honey.personDepart.entity.Tb201;
import project.honey.personDepart.repository.Tb201Repository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class Service020101Impl implements Service020101 {

    private final Tb201Repository repository;
    private final FileConverter fileConverter;

    @Override
    public void insert(Form020101 form) throws IOException {
        String dbFileName = "";
        String dbImgName = "";
        if(!form.getFile().isEmpty()) {
            FileName fileName = fileConverter.uploadFile(form.getFile(), "/upload/empFile/");
            dbFileName = fileName.getDbFileName();
        }
        if(!form.getImg().isEmpty()){
            FileName imgName = fileConverter.uploadFile(form.getImg(), "/upload/empImg/");
            dbImgName = imgName.getDbFileName();
        }
        Tb201 entity = Form020101.toTb201(form, dbFileName, dbImgName);
        repository.save(entity);
    }

    @Override
    public Page<Tb201Dto> findAll(String empNm, String postCd, String deptCd, Pageable pageable) {
        Page<Tb201> result = repository.findAllByDsl(empNm, postCd, deptCd, pageable);
        Page<Tb201Dto> resultList = result.map(Tb201Dto::of);
        return resultList;
    }

    @Override
    public Tb201Dto findById(Integer id) {
        Tb201 entity = repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb201Dto.of(entity);
    }

    @Transactional
    @Override
    public void update(Tb201Dto dto) {
        Tb201 entity = repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        entity.updateData(dto);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
