package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb410_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
public class PrintData040201_1 {
    private String goodsNm;
    private String standard;
    private String unit;
    private Integer qty;
    private Integer price;
    private Integer amt;
    private Integer vat;
    private String note;

    public static PrintData040201_1 of(Tb410_1 entity, String goodsNm, String unit){
        return PrintData040201_1.builder()
                .goodsNm(goodsNm)
                .standard(entity.getStandard())
                .unit(unit)
                .qty(entity.getQty())
                .price(entity.getPrice())
                .amt(entity.getAmt())
                .vat(entity.getVat())
                .note(entity.getNote())
                .build();
    }
}
