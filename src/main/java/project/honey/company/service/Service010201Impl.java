package project.honey.company.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.company.dto.Tb102Dto;
import project.honey.company.entity.Tb102;
import project.honey.company.repository.Tb102Repository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class Service010201Impl implements Service010201{

    private final Tb102Repository tb102Repository;

    @Override
    @Transactional
    public void save(Tb102Dto dto) {
        tb102Repository.save(dtoToEntity(dto));
    }

    @Override
    public Page<Tb102Dto> findAll(Pageable pageable) {
        Page<Tb102> result = tb102Repository.findAll(pageable);
        Page<Tb102Dto> resultList = result.map(this::entityToDto);
        return resultList;
    }

    @Override
    public Tb102Dto findById(Integer id) {

        Tb102 entity = tb102Repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 통장을 찾을 수 없습니다"));
        return entityToDto(entity);
    }

    private Tb102Dto entityToDto(Tb102 entity){
        return Tb102Dto.builder()
                .seq(entity.getSeq())
                .fk_tb_101(entity.getFk_tb_101())
                .accountno(entity.getAccountno())
                .banknm(entity.getBanknm())
                .accounhd(entity.getAccounhd())
                .usenote(entity.getUsenote())
                .accountid(entity.getAccountid())
                .stdate(entity.getStdate())
                .note(entity.getNote())
                .note1(entity.getNote1())
                .useyn(entity.getUseyn())
                .createId(entity.getCreateId())
                .createDate(entity.getCreateDate())
                .updateId(entity.getUpdateId())
                .updateDate(entity.getUpdateDate())
                .build();
    }

    private Tb102 dtoToEntity(Tb102Dto dto){
        return Tb102.builder()
                .seq(dto.getSeq())
                .fk_tb_101(dto.getFk_tb_101())
                .accountno(dto.getAccountno())
                .banknm(dto.getBanknm())
                .accounhd(dto.getAccounhd())
                .usenote(dto.getUsenote())
                .accountid(dto.getAccountid())
                .stdate(dto.getStdate())
                .note(dto.getNote())
                .note1(dto.getNote1())
                .useyn(dto.getUseyn())
                .build();
    }
}
