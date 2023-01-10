package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb411_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
public class PrintData040202_1 {
    private String goodsNm;
    private String standard;
    private String unit;
    private Integer qty;
    private Integer price;
    private Integer amt;
    private Integer vat;

    public static PrintData040202_1 of(Tb411_1 tb411_1, String goodsNm, String unit){
        return PrintData040202_1.builder()
                .goodsNm(goodsNm)
                .standard(tb411_1.getStandard())
                .unit(unit)
                .qty(tb411_1.getQty())
                .price(tb411_1.getPrice())
                .amt(tb411_1.getAmt())
                .vat(tb411_1.getVat())
                .build();
    }
}
