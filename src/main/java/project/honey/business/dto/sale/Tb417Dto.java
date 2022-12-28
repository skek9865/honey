package project.honey.business.dto.sale;

import lombok.*;
import project.honey.business.entity.sale.Tb417;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb417Dto {

    private Integer seq;
    private String amountDt;
    private Integer amountNo;
    private String amountCl;
    private String amountTy;
    private Integer tb102;
    private String tb102Nm;
    private Integer price;
    private Integer amount;
    private String custCd;
    private String custNm;
    private String empNo;
    private String note;
    private String note1;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb417Dto (String amountDt, Integer num){
        this.amountDt = amountDt;
        this.amountNo = num;
        this.price = num;
        this.amount = num;
    }

    public static Tb417Dto of(Tb417 entity, String amountClNm, String tb102Nm, String custNm, String empNm){
        String amountDt = entity.getAmountDt().substring(0,4) + "-" + entity.getAmountDt().substring(4,6) + "-" + entity.getAmountDt().substring(6,8);
        if(amountClNm == null) amountClNm = entity.getAmountCl();
        if(empNm == null) empNm = entity.getEmpNo();

        return Tb417Dto.builder()
                .seq(entity.getSeq())
                .amountDt(amountDt)
                .amountNo(entity.getAmountNo())
                .amountCl(amountClNm)
                .amountTy(entity.getAmountTy())
                .tb102(entity.getTb102().getSeq())
                .tb102Nm(tb102Nm)
                .price(entity.getPrice())
                .amount(entity.getAmount())
                .custCd(entity.getCustCd())
                .custNm(custNm)
                .empNo(empNm)
                .note(entity.getNote())
                .note1(entity.getNote1())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();

    }
}
