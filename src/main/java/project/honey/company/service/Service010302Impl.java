package project.honey.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.CodeToName;
import project.honey.company.dto.Tb107Dto;
import project.honey.company.entity.Tb107;
import project.honey.company.form.Tb107Form;
import project.honey.company.repository.Tb107Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Service010302Impl implements Service010302{

    private final Tb107Repository tb107Repository;
    private final CodeToName codeToName;

    @Override
    public Boolean insert(Tb107Form form) {
        Tb107 entity = Tb107Form.toTb107(form);
        tb107Repository.save(entity);
        return true;
    }

    @Override
    public Page<Tb107Dto> findAll(Pageable pageable) {
        Page<Tb107> result = tb107Repository.findAll(pageable);
        List<Tb107Dto> resultList = new ArrayList<>();
        Map<String, String> empMap = codeToName.emp();
        result.forEach(e -> {
            String empNm = empMap.get(e.getEmpNo());
            Tb107Dto dto = Tb107Dto.of(e, empNm);
            resultList.add(dto);
        });
        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel() {
        List<Tb107> result = tb107Repository.findAll();
        List<List<String>> resultList = new ArrayList<>();
        Map<String, String> empMap = codeToName.emp();
        result.forEach(e -> {
            String empNm = empMap.get(e.getEmpNo());
            String instSDt = e.getInstSDt().substring(0, 4) + "-" + e.getInstSDt().substring(4, 6) + "-" + e.getInstSDt().substring(6, 8);
            String instEDt = e.getInstEDt().substring(0, 4) + "-" + e.getInstEDt().substring(4, 6) + "-" + e.getInstEDt().substring(6, 8);
            List<String> list = new ArrayList<>();

            list.add(e.getCarNm());
            list.add(e.getCarYear());
            list.add(e.getCarNo());
            list.add(empNm);
            list.add(e.getInstNm());
            list.add(instSDt);
            list.add(instEDt);
            list.add(e.getAgeLimit());
            list.add(String.valueOf(e.getInstAmt()));
            list.add(e.getNote());

            resultList.add(list);
        });
        return resultList;
    }

    @Override
    public Tb107Dto findById(Integer id) {
        Tb107 entity = tb107Repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb107Dto.of(entity, null);
    }

    @Transactional
    @Override
    public Boolean update(Tb107Form form) {
        Tb107 entity = tb107Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);
        entity.updateData(form);
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        tb107Repository.deleteById(id);
        return true;
    }
}
