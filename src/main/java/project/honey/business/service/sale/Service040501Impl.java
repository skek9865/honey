package project.honey.business.service.sale;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.dto.sale.Tb417Dto;
import project.honey.business.entity.sale.Tb417;
import project.honey.business.form.sale.Tb417Form;
import project.honey.business.repository.sale.Tb417Repository;
import project.honey.comm.CodeToName;
import project.honey.company.entity.Tb102;
import project.honey.company.repository.Tb102Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Service040501Impl implements Service040501{

    private final Tb417Repository tb417Repository;
    private final Tb102Repository tb102Repository;
    private final CodeToName codeToName;

    @Override
    public Boolean insert(Tb417Form form) {
        Tb102 tb102 = tb102Repository.findById(form.getTb102()).orElseThrow(RuntimeException::new);
        Tb417 entity = Tb417Form.toTb417(form, tb102);
        tb417Repository.save(entity);
        return true;
    }

    @Override
    public Page<Tb417Dto> findAll(Pageable pageable) {
        Page<Tb417> result = tb417Repository.findAll(pageable);

        List<Tb417Dto> resultList = new ArrayList<>();

        Map<Integer, String> tb102Map = tb102Repository.findAll().stream().collect(Collectors.toMap(Tb102::getSeq, Tb102::getAccountno));
        Map<String, String> empMap = codeToName.emp();
        Map<String, String> amountClMap = codeToName.commonCode("14");
        Map<String, String> custMap = codeToName.cust();

        for (Tb417 entity : result.getContent()) {

            String amountClNm = amountClMap.get(entity.getAmountCl());
            String tb102Nm = tb102Map.get(entity.getTb102().getSeq());
            String custNm = custMap.get(entity.getCustCd());
            String empNm = empMap.get(entity.getEmpNo());

            Tb417Dto dto = Tb417Dto.of(entity, amountClNm, tb102Nm, custNm, empNm);
            resultList.add(dto);
        }
        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel() {
        List<Tb417> result = tb417Repository.findAll();

        List<List<String>> resultList = new ArrayList<>();

        Map<Integer, String> tb102Map = tb102Repository.findAll().stream().collect(Collectors.toMap(Tb102::getSeq, Tb102::getAccountno));
        Map<String, String> empMap = codeToName.emp();
        Map<String, String> amountClMap = codeToName.commonCode("14");
        Map<String, String> custMap = codeToName.cust();

        for (Tb417 entity : result) {
            List<String> list = new ArrayList<>();

            String amountDt = entity.getAmountDt().substring(0,4) + "-" + entity.getAmountDt().substring(4,6) + "-" + entity.getAmountDt().substring(6,8);

            String amountClNm = amountClMap.get(entity.getAmountCl());
            String custNm = custMap.get(entity.getCustCd());
            String empNm = empMap.get(entity.getEmpNo());
            String tb102Nm = "";
            if(entity.getTb102() != null) {
                tb102Nm = tb102Map.get(entity.getTb102().getSeq());
            }

            list.add(amountDt);
            list.add(String.valueOf(entity.getAmountNo()));
            list.add(amountClNm);
            list.add(entity.getAmountTy());
            list.add(tb102Nm);
            list.add(String.valueOf(entity.getPrice()));
            list.add(String.valueOf(entity.getAmount()));
            list.add(custNm);
            list.add(empNm);
            list.add(entity.getNote());
            list.add(entity.getNote1());

            resultList.add(list);
        }
        return resultList;
    }

    @Override
    public Tb417Dto findById(Integer id) {
        Map<String, String> custMap = codeToName.cust();
        Tb417 entity = tb417Repository.findById(id).orElseThrow(RuntimeException::new);
        String custNm = custMap.get(entity.getCustCd());
        Tb417Dto result = Tb417Dto.of(entity, null, null, custNm, null);
        return result;
    }

    @Transactional
    @Override
    public Boolean update(Tb417Form form) {
        Tb102 tb102 = tb102Repository.findById(form.getTb102()).orElseThrow(RuntimeException::new);
        Tb417 entity = tb417Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);
        entity.updateData(form, tb102);
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        tb417Repository.deleteById(id);
        return true;
    }
}
