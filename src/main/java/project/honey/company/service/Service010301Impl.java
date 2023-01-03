package project.honey.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.CodeToName;
import project.honey.company.dto.Tb106Dto;
import project.honey.company.dto.Tb106_1Dto;
import project.honey.company.entity.Tb106;
import project.honey.company.entity.Tb106_1;
import project.honey.company.form.Tb106Form;
import project.honey.company.form.Tb106_1Form;
import project.honey.company.repository.Tb106Repository;
import project.honey.company.repository.Tb106_1Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Service010301Impl implements Service010301{

    private final Tb106Repository tb106Repository;
    private final Tb106_1Repository tb106_1Repository;
    private final CodeToName codeToName;

    @Override
    public Boolean insert(Tb106Form form) {
        Tb106 entity = Tb106Form.toTb106(form);
        tb106Repository.save(entity);
        return true;
    }

    @Override
    public Page<Tb106Dto> findAll(Pageable pageable) {
        Page<Tb106> result = tb106Repository.findAll(pageable);
        Map<String, String> empMap = codeToName.emp();
        List<Tb106Dto> resultList = new ArrayList<>();
        result.forEach(e -> {
            String empNm = empMap.get(e.getEmpNo());
            Tb106Dto dto = Tb106Dto.of(e, empNm);
            resultList.add(dto);
        });
        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel() {
        List<Tb106> result = tb106Repository.findAll();
        Map<String, String> empMap = codeToName.emp();
        List<List<String>> resultList = new ArrayList<>();
        result.forEach(e -> {
            String empNm = empMap.get(e.getEmpNo());
            String buyDt = e.getBuyDt().substring(0,4) + "-" + e.getBuyDt().substring(4,6) + "-" + e.getBuyDt().substring(6,8);

            List<String> list = new ArrayList<>();

            list.add(e.getCarNm());
            list.add(e.getCarYear());
            list.add(e.getCarNo());
            list.add(empNm);
            list.add(e.getInstNm());
            list.add(buyDt);
            list.add(String.valueOf(e.getBuyAmt()));
            list.add(e.getNote());
            resultList.add(list);
        });
        return resultList;
    }

    @Override
    public Tb106Dto findById(Integer id) {
        Tb106 entity = tb106Repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb106Dto.of(entity, null);
    }

    @Transactional
    @Override
    public Boolean update(Tb106Form form) {
        Tb106 entity = tb106Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);
        entity.updateData(form);
        return true;
    }

    @Transactional
    @Override
    public Boolean delete(Integer id) {
        List<Tb106_1> fkList = tb106_1Repository.findAllByFk(id);
        if(fkList.size() > 0){
            fkList.forEach(e -> tb106_1Repository.deleteById(e.getSeq()));
        }
        tb106Repository.deleteById(id);
        return true;
    }

    @Override
    public Boolean insertFk(Tb106_1Form form) {
        Tb106_1 entity = Tb106_1Form.toTb106_1(form);
        tb106_1Repository.save(entity);
        return true;
    }

    @Override
    public List<Tb106_1Dto> findAllFK(Integer fk) {
        List<Tb106_1> result = tb106_1Repository.findAllByFk(fk);
        Map<String, String> empMap = codeToName.emp();
        List<Tb106_1Dto> resultList = new ArrayList<>();
        result.forEach(e -> {
            String empNm = empMap.get(e.getEmpNo());
            Tb106_1Dto dto = Tb106_1Dto.of(e, empNm);
            resultList.add(dto);
        });
        return resultList;
    }

    @Override
    public List<List<String>> findAllByExcelFk(Integer fk) {
        List<Tb106_1> result = tb106_1Repository.findAllByFk(fk);
        Map<String, String> empMap = codeToName.emp();
        List<List<String>> resultList = new ArrayList<>();
        result.forEach(e -> {
            String empNm = empMap.get(e.getEmpNo());
            String dvStDt = e.getDvStDt().substring(0,4) + "-" + e.getDvStDt().substring(4,6) + "-" + e.getDvStDt().substring(6,8);
            String dvEdDt = e.getDvEdDt().substring(0,4) + "-" + e.getDvEdDt().substring(4,6) + "-" + e.getDvEdDt().substring(6,8);

            List<String> list = new ArrayList<>();
            list.add(dvStDt);
            list.add(dvEdDt);
            list.add(empNm);
            list.add(String.valueOf(e.getPrice()));
            list.add(String.valueOf(e.getPenalty()));
            list.add(e.getPlace());
            list.add(e.getPurpose());
            list.add(e.getNote());
            resultList.add(list);
        });
        return resultList;
    }

    @Override
    public Tb106_1Dto findByIdFk(Integer id) {
        Tb106_1 entity = tb106_1Repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb106_1Dto.of(entity, null);
    }

    @Transactional
    @Override
    public Boolean updateFk(Tb106_1Form form) {
        Tb106_1 entity = tb106_1Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);
        entity.updateData(form);
        return true;
    }

    @Override
    public Boolean deleteFk(Integer id) {
        tb106_1Repository.deleteById(id);
        return true;
    }
}
