package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb410;
import project.honey.business.entity.manage.Tb410_1;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb410MainDto {
    private Integer seq;
    private String est;
    private String custCd;
    private String empNo;
    private String goodsCd;
    private String expDt;
    private Integer price;
    private String status;
    private String prt;
    private String noteP;

    public static Tb410MainDto of(Tb410 entity, String est, String goods, Integer price,
                                  String empNm, String statusNm, String prt, String custNm){

        return Tb410MainDto.builder()
                .seq(entity.getSeq())
                .empNo(empNm)
                .est(est)
                .custCd(custNm)
                .goodsCd(goods)
                .price(price)
                .expDt(entity.getExpDt())
                .noteP(entity.getNote())
                .prt(prt)
                .status(statusNm)
                .build();
    }
}
