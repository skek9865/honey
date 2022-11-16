package project.honey.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.honey.company.CompanyForm;
import project.honey.company.CompanyInfoDto;
import project.honey.company.entity.Tb101;
import project.honey.company.repository.CompanyRepository;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public void save(CompanyForm form){

    }

    public CompanyInfoDto findById(int id){
        Tb101 entity = companyRepository.findById(id).get();
        return entityToDto(entity);
    }


    public CompanyInfoDto entityToDto(Tb101 entity){
        return CompanyInfoDto.builder()
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
}
