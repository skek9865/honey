package project.honey.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.company.dto.Tb109Dto;
import project.honey.company.entity.Tb109;
import project.honey.company.form.Tb109Form;
import project.honey.company.repository.Tb109Repository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Service010304Impl implements Service010304{

    private final Tb109Repository tb109Repository;

    @Override
    public Boolean insert(Tb109Form form) {
        Tb109 entity = Tb109Form.toTb109(form);
        tb109Repository.save(entity);
        return true;
    }

    @Override
    public Page<Tb109Dto> findAll(Pageable pageable) {
        Page<Tb109> result = tb109Repository.findAll(pageable);
        Page<Tb109Dto> resultList = result.map(Tb109Dto::of);
        return resultList;
    }

    @Override
    public List<List<String>> findAllByExcel() {
        List<Tb109> result = tb109Repository.findAll();
        List<List<String>> resultList = new ArrayList<>();
        result.forEach(e -> {
            List<String> list = new ArrayList<>();

            list.add(e.getCompany());
            list.add(e.getTelNo());
            list.add(e.getMobile());
            list.add(e.getFax());
            list.add(e.getEmail());
            list.add(e.getEtcTel1());
            list.add(e.getEtcTel2());
            list.add(e.getEtcTel3());
            list.add(e.getNote());

            resultList.add(list);
        });
        return resultList;
    }

    @Override
    public Tb109Dto findById(Integer id) {
        Tb109 entity = tb109Repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb109Dto.of(entity);
    }

    @Transactional
    @Override
    public Boolean update(Tb109Form form) {
        Tb109 entity = tb109Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);
        entity.updateData(form);
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        tb109Repository.deleteById(id);
        return true;
    }
}
