package project.honey.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;
import project.honey.company.dto.Tb102Dto;
import project.honey.company.dto.Tb103Dto;
import project.honey.company.entity.Tb103;
import project.honey.company.repository.Tb103Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service010202Impl implements Service010202{

    private final Tb103Repository tb103Repository;
    private final CodeToName codeToName;

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
        return tb103.getSeq();
    }

    @Override
    public Page<Tb103Dto> findAll(Pageable pageable) {
        Page<Tb103> result = tb103Repository.findAll(pageable);
        Page<Tb103Dto> resultList = result.map(this::entityToDto);
        return resultList;
    }

    @Override
    public List<Tb103Dto> findAll() {
        List<Tb103> entityList = tb103Repository.findAll();
        List<Tb103Dto> dtoList = entityList.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
        return dtoList;
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

    @Override
    public List<List<String>> findAllByExcel() {
        List<Tb103Dto> tb103DtoList = tb103Repository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
        List<List<String>> excelData = new ArrayList<>();
        for(Tb103Dto dto : tb103DtoList){
            List<String> list = new ArrayList<>();
            list.add(dto.getCernm());
            list.add(dto.getExpdt());
            list.add(dto.getUsenote());
            list.add(dto.getSavemtd());
            list.add(dto.getEmpnm());
            list.add(dto.getUseyn());
            list.add(dto.getNote());
            excelData.add(list);
        }
        return excelData;
    }

    public Map<Integer, String> cer(){
        return tb103Repository.findAll().stream()
                .collect(Collectors.toMap(Tb103::getSeq, Tb103::getCernm));
    }

    private Tb103Dto entityToDto(Tb103 entity){
        String expdt = GlobalMethod.makeYmd(entity.getExpdt(), "yyyy-MM-dd");

        Map<String, String> emp = codeToName.emp();
        String empNm = emp.get(entity.getEmpno());
        return Tb103Dto.builder()
                .seq(entity.getSeq())
                .fk_tb_101(entity.getFk_tb_101())
                .cernm(entity.getCernm())
                .expdt(expdt)
                .usenote(entity.getUsenote())
                .savemtd(entity.getSavemtd())
                .empno(entity.getEmpno())
                .empnm(empNm)
                .useyn(entity.getUseyn())
                .note(entity.getNote())
                .createId(entity.getCreateId())
                .createDate(entity.getCreateDate())
                .updateId(entity.getUpdateId())
                .updateDate(entity.getUpdateDate())
                .build();
    }

    private Tb103 dtoToEntity(Tb103Dto dto){
        if(dto.getFk_tb_101()==null) dto.setFk_tb_101(27);
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
