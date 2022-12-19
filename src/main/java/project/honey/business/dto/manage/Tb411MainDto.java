package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb411;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb411MainDto {

    private Integer seq;
    private String orderNo;
    private String custNm;
    private String empNo;
    private String goodsCd;
    private Integer price;
    private Integer qty;
    private String status;
    private String prt;
    private String note;

    public static Tb411MainDto of (Tb411 entity, String goodsCd, Integer price, Integer qty){
        return Tb411MainDto.builder()
                .seq(entity.getSeq())
                .orderNo(entity.getOrderDt())
                .custNm(entity.getCustCd())
                .empNo(entity.getEmpNo())
                .goodsCd(goodsCd)
                .price(price)
                .qty(qty)
                .status(entity.getStatus())
                .prt(entity.getPrtDt())
                .note(entity.getNote())
                .build();
    }
}
