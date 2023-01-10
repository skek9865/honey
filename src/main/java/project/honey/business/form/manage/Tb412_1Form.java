package project.honey.business.form.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb412;
import project.honey.business.entity.manage.Tb412_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Setter
public class Tb412_1Form {
    private Integer seq;
    private Integer tb412;
    private String goodsCd;
    private String standard;
    private Integer qty;
    private Integer price;
    private Integer amt;
    private Integer vat;
    private Integer amtSum;
    private String note;
    private Integer priceVat;
    private Boolean shipYn;

    public static Tb412_1 toTb412_1(Tb412_1Form form, Tb412 tb412){
        String shipYn = "Y";

        return Tb412_1.builder()
                .tb412(tb412)
                .goodsCd(form.getGoodsCd())
                .standard(form.getStandard())
                .qty(form.getQty())
                .price(form.getPrice())
                .amt(form.getAmt())
                .vat(form.getVat())
                .amtSum(form.getAmtSum())
                .note(form.getNote())
                .priceVat(form.getPriceVat())
                .shipYn(shipYn)
                .build();
    }
}
