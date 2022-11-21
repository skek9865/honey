package project.honey.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.honey.comm.UploadService;
import project.honey.company.CompanyForm;
import project.honey.company.Tb101Dto;
import project.honey.company.entity.Tb101;
import project.honey.company.repository.Tb101Repository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service010101 {

    private final Tb101Repository tb101Repository;

    @Transactional
    public void save(CompanyForm form){
        Tb101 tb101 = tb101Repository.findById(27)
                .orElseThrow(() -> new IllegalArgumentException("회사기본정보를 찾을 수 없습니다."));
        tb101.changeInfo(form);
    }

    public Tb101Dto findById(int id){
        Tb101 entity = tb101Repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회사기본정보를 찾을 수 없습니다."));
        return entityToDto(entity);
    }


    private Tb101Dto entityToDto(Tb101 entity){
        return Tb101Dto.builder()
                .seq(entity.getSeq())
                .corpnm(entity.getCorpnm())
                .corpno(entity.getCorpno())
                .ceonm(entity.getCeonm())
                .setdt(entity.getSetdt())
                .corptel(entity.getCorptel())
                .hometel(entity.getHometel())
                .email(entity.getEmail())
                .mobile(entity.getMobile())
                .corpfax(entity.getCorpfax())
                .hompage(entity.getHompage())
                .zipcd1(entity.getZipcd1())
                .address1(entity.getAddress1())
                .address11(entity.getAddress11())
                .zipcd2(entity.getZipcd2())
                .address2(entity.getAddress2())
                .address21(entity.getAddress21())
                .corpeng(entity.getCorpeng())
                .zipcdeng(entity.getZipcdeng())
                .addresseng(entity.getAddresseng())
                .addresseng1(entity.getAddresseng1())
                .corpregno(entity.getCorpregno())
                .bsns(entity.getBsns())
                .item(entity.getItem())
                .logonm(entity.getLogonm())
                .stampnm(entity.getStampnm())
                .createId(entity.getCreateId())
                .createDate(entity.getCreateDate())
                .updateId(entity.getUpdateId())
                .modifyDate(entity.getModifyDate())
                .build();
    }

    private Tb101 dtoToEntity(Tb101Dto dto){
        return Tb101.builder()
                .seq(dto.getSeq())
                .corpnm(dto.getCorpnm())
                .corpno(dto.getCorpno())
                .ceonm(dto.getCeonm())
                .setdt(dto.getSetdt())
                .corptel(dto.getCorptel())
                .hometel(dto.getHometel())
                .email(dto.getEmail())
                .mobile(dto.getMobile())
                .corpfax(dto.getCorpfax())
                .hompage(dto.getHompage())
                .zipcd1(dto.getZipcd1())
                .address1(dto.getAddress1())
                .address11(dto.getAddress11())
                .zipcd2(dto.getZipcd2())
                .address2(dto.getAddress2())
                .address21(dto.getAddress21())
                .corpeng(dto.getCorpeng())
                .zipcdeng(dto.getZipcdeng())
                .addresseng(dto.getAddresseng())
                .addresseng1(dto.getAddresseng1())
                .corpregno(dto.getCorpregno())
                .bsns(dto.getBsns())
                .item(dto.getItem())
                .logonm(dto.getLogonm())
                .stampnm(dto.getStampnm())
                .build();
    }
}
