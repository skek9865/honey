package project.honey.company.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.company.dto.Tb102Dto;

import java.awt.print.Pageable;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class Service010201Impl implements Service010201{
    @Override
    @Transactional
    public void save(Tb102Dto dto) {

    }

    @Override
    public Page<Tb102Dto> findAll(Pageable pageable) {

        return null;
    }
}
