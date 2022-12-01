package project.honey.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.honey.business.dto.Tb402Dto;
import project.honey.business.form.Tb402Form;
import project.honey.business.repository.Tb402Repository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Service040102Impl implements Service040102{

    private final Tb402Repository tb402Repository;

    @Override
    public Boolean insert(Tb402Form form) {
        return null;
    }

    @Override
    public Page<Tb402Dto> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Tb402Dto> findAllByExcel() {
        return null;
    }

    @Override
    public Tb402Dto findById(Integer id) {
        return null;
    }

    @Override
    public Boolean update(Tb402Form form) {
        return null;
    }

    @Override
    public Boolean delete(Integer id) {
        return null;
    }
}
