package project.honey.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.CodeToName;
import project.honey.comm.UploadService;
import project.honey.company.dto.Tb108Dto;
import project.honey.company.entity.Tb108;
import project.honey.company.form.Tb108Form;
import project.honey.company.repository.Tb108Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Service010303Impl implements Service010303{

    private final Tb108Repository tb108Repository;
    private final CodeToName codeToName;
    private final UploadService uploadService;

    @Override
    public Boolean insert(Tb108Form form) throws IOException{
        String picNm = "";
        if(!form.getImg().isEmpty()) picNm = uploadService.uploadFileUuid(form.getImg(), "C:/study/spring/team/honey/src/main/resources/static/files/patent");
        Tb108 entity = Tb108Form.toTb108(form, picNm);
        tb108Repository.save(entity);
        return true;
    }

    @Override
    public Page<Tb108Dto> findAll(Pageable pageable) {
        Page<Tb108> result = tb108Repository.findAll(pageable);
        Page<Tb108Dto> resultList = result.map(Tb108Dto::of);
        return resultList;
    }

    @Override
    public List<List<String>> findAllByExcel() {
        List<Tb108> result = tb108Repository.findAll();
        List<List<String>> resultList = new ArrayList<>();
        result.forEach(e -> {
            String patentDt = e.getPatentDt().substring(0, 4) + "-" + e.getPatentDt().substring(4, 6) + "-" + e.getPatentDt().substring(6, 8);
            String regDt = e.getRegDt().substring(0, 4) + "-" + e.getRegDt().substring(4, 6) + "-" + e.getRegDt().substring(6, 8);
            List<String> list = new ArrayList<>();

            list.add(e.getPart());
            list.add(e.getPatentNo());
            list.add(e.getPatentApNo());
            list.add(patentDt);
            list.add(regDt);
            list.add(e.getPatentNm());
            list.add(e.getPatentMan());
            list.add(e.getInventor());
            list.add(e.getIssueDBy());
            list.add(e.getNote());

            resultList.add(list);
        });
        return null;
    }

    @Override
    public Tb108Dto findById(Integer id) {
        Tb108 entity = tb108Repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb108Dto.of(entity);
    }

    @Transactional
    @Override
    public Boolean update(Tb108Form form) throws IOException {
        Tb108 entity = tb108Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);
        String picNm = entity.getPicNm();

        if(!form.getImg().isEmpty()){
            uploadService.deleteFileUuid("C:/study/spring/team/honey/src/main/resources/static/files/patent", picNm);
            picNm = uploadService.uploadFileUuid(form.getImg(), "C:/study/spring/team/honey/src/main/resources/static/files/patent");
        }

        entity.updateData(form, picNm);
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        Tb108 entity = tb108Repository.findById(id).orElseThrow(RuntimeException::new);
        tb108Repository.deleteById(id);
        uploadService.deleteFileUuid("C:/study/spring/team/honey/src/main/resources/static/files/patent", entity.getPicNm());
        return true;
    }
}
