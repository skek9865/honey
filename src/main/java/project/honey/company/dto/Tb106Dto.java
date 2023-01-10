package project.honey.company.dto;

import lombok.*;
import project.honey.company.entity.Tb106;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb106Dto {
    private Integer seq;
    private Integer tb101;
    private String carNm;
    private String carYear;
    private String carNo;
    private String empNo;
    private String instNm;
    private String buyDt;
    private Integer buyAmt;
    private String note;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb106Dto(String date, int num, int fk){
        this.tb101 = fk;
        this.buyDt = date;
        this.buyAmt = num;
    }

    public static Tb106Dto of(Tb106 entity, String empNm){
        if(empNm == null) empNm = entity.getEmpNo();
        String buyDt = entity.getBuyDt().substring(0,4) + "-" + entity.getBuyDt().substring(4,6) + "-" + entity.getBuyDt().substring(6,8);

        return Tb106Dto.builder()
                .seq(entity.getSeq())
                .tb101(entity.getTb101())
                .carNm(entity.getCarNm())
                .carYear(entity.getCarYear())
                .carNo(entity.getCarNo())
                .empNo(empNm)
                .instNm(entity.getInstNm())
                .buyDt(buyDt)
                .buyAmt(entity.getBuyAmt())
                .note(entity.getNote())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
