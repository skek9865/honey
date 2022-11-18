package project.honey.personDepart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.personDepart.dto.Tb201Dto;
import project.honey.personDepart.entity.Tb201;
import project.honey.personDepart.repository.Tb201Repository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Service020101Impl implements Service020101 {

    private final Tb201Repository repository;

    @Override
    public void insert(Tb201Dto tb201Dto) {
        Tb201 entity = dtoToEntity(tb201Dto);
        repository.save(entity);
    }

    @Override
    public List<Tb201Dto> findAll(String empNm, String postCd, String deptCd) {
        List<Tb201> result = repository.findAllByDsl(empNm, postCd, deptCd);
        List<Tb201Dto> resultList = result.stream().map(entity -> entityToDto(entity)).collect(Collectors.toList());
        return resultList;
    }

    @Override
    public Tb201Dto findById(Integer id) {
        Tb201 entity = repository.findById(id).orElseThrow(() -> new RuntimeException());
        return entityToDto(entity);
    }

    @Transactional
    @Override
    public void update(Tb201Dto dto) {
        Tb201 entity = repository.findById(dto.getSeq()).orElseThrow(() -> new RuntimeException());
        entity.updateData(dto);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    private Tb201 dtoToEntity(Tb201Dto dto) {
        Tb201 entity = Tb201.builder()
                .empNo(dto.getEmpNo())
                .empNm(dto.getEmpNm())
                .emp2Nm(dto.getEmp2Nm())
                .empEngNm(dto.getEmpEngNm())
                .idNo(dto.getIdNo())
                .headYn(dto.getHeadYn())
                .empDt(dto.getEmpDt())
                .empClass(dto.getEmpClass())
                .post(dto.getPost())
                .post1(dto.getPost1())
                .leaveDt(dto.getLeaveDt())
                .leaveRs(dto.getLeaveRs())
                .phone(dto.getPhone())
                .mobile(dto.getMobile())
                .psNo(dto.getPsNo())
                .email(dto.getEmail())
                .deptCd(dto.getDeptCd())
                .workCd(dto.getWorkCd())
                .bankNm(dto.getBankNm())
                .aCutNo(dto.getACutNo())
                .aCutNm(dto.getACutNm())
                .zipCd(dto.getZipCd())
                .address(dto.getAddress())
                .address1(dto.getAddress1())
                .picNm(dto.getPicNm())
                .note(dto.getNote())
                .fileNm(dto.getFileNm())
                .build();
        return entity;
    }

    private Tb201Dto entityToDto(Tb201 entity){
        Tb201Dto dto = Tb201Dto.builder()
                .seq(entity.getSeq())
                .empNo(entity.getEmpNo())
                .empNm(entity.getEmpNm())
                .emp2Nm(entity.getEmp2Nm())
                .empEngNm(entity.getEmpEngNm())
                .idNo(entity.getIdNo())
                .headYn(entity.getHeadYn())
                .empDt(entity.getEmpDt())
                .empClass(entity.getEmpClass())
                .post(entity.getPost())
                .post1(entity.getPost1())
                .leaveDt(entity.getLeaveDt())
                .leaveRs(entity.getLeaveRs())
                .phone(entity.getPhone())
                .mobile(entity.getMobile())
                .psNo(entity.getPsNo())
                .email(entity.getEmail())
                .deptCd(entity.getDeptCd())
                .workCd(entity.getWorkCd())
                .bankNm(entity.getBankNm())
                .aCutNo(entity.getACutNo())
                .aCutNm(entity.getACutNm())
                .zipCd(entity.getZipCd())
                .address(entity.getAddress())
                .address1(entity.getAddress1())
                .picNm(entity.getPicNm())
                .note(entity.getNote())
                .fileNm(entity.getFileNm())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .modifyDate(entity.getModifyDate())
                .updateId(entity.getUpdateId())
                .build();

        return dto;
    }
}
