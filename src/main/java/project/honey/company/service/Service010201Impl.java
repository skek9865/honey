package project.honey.company.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.company.dto.Tb102Dto;
import project.honey.company.entity.Tb102;
import project.honey.company.entity.Tb103;
import project.honey.company.repository.Tb102Repository;
import project.honey.pay.dto.Tb301Dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class Service010201Impl implements Service010201{

    private final Tb102Repository tb102Repository;

    @Override
    @Transactional
    public Integer insert(Tb102Dto dto) {
        // date 수정 , useyn수정
        dto.beforeProcess();
        return tb102Repository.save(dtoToEntity(dto)).getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb102Dto dto) {
        dto.beforeProcess();
        Tb102 entity = tb102Repository.findById(dto.getSeq())
                .orElseThrow(() -> new IllegalArgumentException("해당 통장을 찾을 수 없습니다"));
        entity.changeInfo(dto);
        return entity.getSeq();
    }

    @Override
    public Page<Tb102Dto> findAll(Pageable pageable) {
        Page<Tb102> result = tb102Repository.findAll(pageable);
        Page<Tb102Dto> resultList = result.map(this::entityToDto);
        return resultList;
    }

    @Override
    public List<Tb102Dto> findAll() {
        List<Tb102> entityList = tb102Repository.findAll();
        List<Tb102Dto> dtoList = entityList.stream()
                .map(e -> entityToDto(e))
                .collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public Tb102Dto findById(Integer id) {
        Tb102 entity = tb102Repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 통장을 찾을 수 없습니다"));
        return entityToDto(entity);
    }

    @Override
    @Transactional
    public Integer delete(Integer id) {
        tb102Repository.deleteById(id);
        return id;
    }

    @Override
    public List<List<String>> findAllByExcel() {
        List<Tb102Dto> tb102DtoList = tb102Repository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
        List<List<String>> excelData = new ArrayList<>();
        for(Tb102Dto dto : tb102DtoList){
            List<String> list = new ArrayList<>();
            list.add(dto.getAccountno());
            list.add(dto.getBanknm());
            list.add(dto.getAccounhd());
            list.add(dto.getUsenote());
            list.add(dto.getAccountid());
            list.add(dto.getStdate());
            list.add(dto.getNote());
            list.add(dto.getNote1());
            list.add(dto.getUseyn());
            excelData.add(list);
        }
        return excelData;
    }

    @Override
    public Map<Integer, String> bank() {
        return tb102Repository.findAll().stream()
                .collect(Collectors.toMap(Tb102::getSeq, Tb102::getAccountno));
    }

    private Tb102Dto entityToDto(Tb102 entity){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat beforeSdf= new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try{
            date = beforeSdf.parse(entity.getStdate());
        } catch(ParseException e){
            e.printStackTrace();
        }
        String stdate = sdf.format(date);

        return Tb102Dto.builder()
                .seq(entity.getSeq())
                .fk_tb_101(entity.getFk_tb_101())
                .accountno(entity.getAccountno())
                .banknm(entity.getBanknm())
                .accounhd(entity.getAccounhd())
                .usenote(entity.getUsenote())
                .accountid(entity.getAccountid())
                .stdate(stdate)
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

        if(dto.getFk_tb_101()==null) dto.setFk_tb_101(27);

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
