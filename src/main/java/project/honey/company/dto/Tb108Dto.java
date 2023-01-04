package project.honey.company.dto;

import lombok.*;
import project.honey.company.entity.Tb108;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb108Dto {

    private Integer seq;
    private Integer tb101;
    private String part;
    private String patentNo;
    private String patentApNo;
    private String patentDt;
    private String regDt;
    private String patentNm;
    private String patentMan;
    private String inventor;
    private String issueDBy;
    private String note;
    private String picNm;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb108Dto (String date){
        this.patentDt = date;
        this.regDt = date;
        this.tb101 = 27;
    }

    public static Tb108Dto of (Tb108 entity){
        String patentDt = entity.getPatentDt().substring(0, 4) + "-" + entity.getPatentDt().substring(4, 6) + "-" + entity.getPatentDt().substring(6, 8);
        String regDt = entity.getRegDt().substring(0, 4) + "-" + entity.getRegDt().substring(4, 6) + "-" + entity.getRegDt().substring(6, 8);

        return Tb108Dto.builder()
                .seq(entity.getSeq())
                .tb101(entity.getTb101())
                .part(entity.getPart())
                .patentNo(entity.getPatentNo())
                .patentApNo(entity.getPatentApNo())
                .patentDt(patentDt)
                .regDt(regDt)
                .patentNm(entity.getPatentNm())
                .patentMan(entity.getPatentMan())
                .inventor(entity.getInventor())
                .issueDBy(entity.getIssueDBy())
                .note(entity.getNote())
                .picNm(entity.getPicNm())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
