package project.honey.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.company.dto.Tb103Dto;
import project.honey.company.entity.Tb103;
import project.honey.company.repository.Tb103Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service010202Impl implements Service010202{

    private final Tb103Repository tb103Repository;

    @Transactional
    @Override
    public Integer insert(Tb103Dto dto) {
        dto.beforeProcess();
        Tb103 tb103 = tb103Repository.save(dtoToEntity(dto));
        return tb103.getSeq();
    }

    @Transactional
    @Override
    public Integer update(Tb103Dto dto) {
        dto.beforeProcess();
        Tb103 tb103 = tb103Repository.findById(dto.getSeq())
                .orElseThrow(() -> new IllegalArgumentException("해당 인증서를 찾을 수 없습니다."));
        tb103.changInfo(dto);
        return null;
    }

    @Override
    public Page<Tb103Dto> findAll(Pageable pageable) {
        Page<Tb103> result = tb103Repository.findAll(pageable);
        Page<Tb103Dto> resultList = result.map(this::entityToDto);
        return resultList;
    }

    @Override
    public Tb103Dto findById(Integer id) {
        Tb103 tb103 = tb103Repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 인증서를 찾을 수 없습니다."));
        return entityToDto(tb103);
    }

    @Transactional
    @Override
    public Integer delete(Integer id) {
        tb103Repository.deleteById(id);
        return id;
    }

    private Tb103Dto entityToDto(Tb103 entity){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat beforeSdf= new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try{
            date = beforeSdf.parse(entity.getExpdt());
        } catch(ParseException e){
            e.printStackTrace();
        }
        String expdt = sdf.format(date);

        return Tb103Dto.builder()
                .seq(entity.getSeq())
                .fk_tb_101(entity.getFk_tb_101())
                .cernm(entity.getCernm())
                .expdt(expdt)
                .usenote(entity.getUsenote())
                .savemtd(entity.getSavemtd())
                .empno(entity.getEmpno())
                .useyn(entity.getUseyn())
                .note(entity.getNote())
                .createId(entity.getCreateId())
                .createDate(entity.getCreateDate())
                .updateId(entity.getUpdateId())
                .updateDate(entity.getUpdateDate())
                .build();
    }

    private Tb103 dtoToEntity(Tb103Dto dto){
        return Tb103.builder()
                .seq(dto.getSeq())
                .fk_tb_101(dto.getFk_tb_101())
                .cernm(dto.getCernm())
                .expdt(dto.getExpdt())
                .usenote(dto.getUsenote())
                .savemtd(dto.getSavemtd())
                .empno(dto.getEmpno())
                .useyn(dto.getUseyn())
                .note(dto.getNote())
                .build();
    }
}
