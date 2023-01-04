package project.honey.company.dto;

import lombok.*;
import project.honey.company.entity.Tb109;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Tb109Dto {

    private Integer seq;
    private Integer tb101;
    private String company;
    private String telNo;
    private String mobile;
    private String fax;
    private String email;
    private String etcTel1;
    private String etcTel2;
    private String etcTel3;
    private String note;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb109Dto(){
        this.tb101 = 27;
    }

    public static Tb109Dto of(Tb109 entity){
        return Tb109Dto.builder()
                .seq(entity.getSeq())
                .tb101(entity.getTb101())
                .company(entity.getCompany())
                .telNo(entity.getTelNo())
                .mobile(entity.getMobile())
                .fax(entity.getFax())
                .email(entity.getEmail())
                .etcTel1(entity.getEtcTel1())
                .etcTel2(entity.getEtcTel2())
                .etcTel3(entity.getEtcTel3())
                .note(entity.getNote())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
