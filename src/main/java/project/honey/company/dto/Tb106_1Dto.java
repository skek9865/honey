package project.honey.company.dto;

import lombok.*;
import project.honey.company.entity.Tb106_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb106_1Dto {
    private Integer seq;
    private Integer tb101;
    private Integer tb106;
    private String dvStDt;
    private String dvEdDt;
    private String empNo;
    private Integer price;
    private Integer penalty;
    private String place;
    private String purpose;
    private String note;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb106_1Dto(String date, int num, int fk, int tb106){
        this.dvStDt = date;
        this.dvEdDt = date;
        this.price = num;
        this.penalty = num;
        this.tb101 = fk;
        this.tb106 = tb106;
    }

    public static Tb106_1Dto of(Tb106_1 entity, String empNm){
        if(empNm == null) empNm = entity.getEmpNo();
        String dvStDt = entity.getDvStDt().substring(0,4) + "-" + entity.getDvStDt().substring(4,6) + "-" + entity.getDvStDt().substring(6,8);
        String dvEdDt = entity.getDvEdDt().substring(0,4) + "-" + entity.getDvEdDt().substring(4,6) + "-" + entity.getDvEdDt().substring(6,8);

        return Tb106_1Dto.builder()
                .seq(entity.getSeq())
                .tb101(entity.getTb101())
                .tb106(entity.getTb106())
                .dvStDt(dvStDt)
                .dvEdDt(dvEdDt)
                .empNo(empNm)
                .price(entity.getPrice())
                .penalty(entity.getPenalty())
                .place(entity.getPlace())
                .purpose(entity.getPurpose())
                .note(entity.getNote())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
