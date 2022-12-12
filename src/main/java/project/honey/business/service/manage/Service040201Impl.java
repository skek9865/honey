package project.honey.business.service.manage;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.dto.manage.Tb410Dto;
import project.honey.business.entity.manage.Tb410;
import project.honey.business.entity.manage.Tb410_1;
import project.honey.business.form.manage.Search040201;
import project.honey.business.form.manage.Tb410Form;
import project.honey.business.form.manage.Tb410_1Form;
import project.honey.business.repository.manage.Tb410Repository;
import project.honey.business.repository.manage.Tb410_1Repository;
import project.honey.system.dto.CodeDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Service040201Impl implements Service040201{

    private final Tb410Repository tb410Repository;
    private final Tb410_1Repository tb410_1Repository;

    @Transactional
    @Override
    public Boolean insert(Tb410Form tb410Form, Tb410_1Form tb410_1Form) {
        Tb410 tb410 = Tb410Form.toTb410(tb410Form);
        Tb410_1 tb410_1 = Tb410_1Form.toTb410_1(tb410_1Form);
        tb410Repository.save(tb410);
        tb410_1Repository.save(tb410_1);
        return true;
    }

    @Override
    public Page<Tb410Dto> findAll(Search040201 search040201, Pageable pageable) {
        
        return null;
    }

    @Override
    public List<List<String>> findAllByExcel(Search040201 search040201) {
        return null;
    }

    @Override
    public Tb410Dto findById(Integer id) {
        return null;
    }

    @Override
    public Boolean update(Tb410Form tb410Form, Tb410_1Form tb410_1Form) {
        return null;
    }

    @Override
    public Boolean delete(Integer id) {
        return null;
    }

    @Override
    public List<CodeDto> findAllBySelect(Integer type) {
        return null;
    }
}
