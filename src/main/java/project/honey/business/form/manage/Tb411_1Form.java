package project.honey.business.form.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb411;
import project.honey.business.entity.manage.Tb411_1;
import project.honey.comm.GlobalMethod;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Setter
public class Tb411_1Form {

    private Integer seq;
    private Integer tb411;
    private String goodsCd;
    private String standard;
    private Integer qty;
    private Integer price;
    private Integer amt;
    private Integer vat;
    private String deadDt;
    private String note;

    public static Tb411_1 toTb411_1(Tb411_1Form form, Integer fk){
        Tb411 tb411 = Tb411.builder().seq(fk).build();
        String deadDt = GlobalMethod.replaceYmd(form.getDeadDt(), "-");

        return Tb411_1.builder()
                .tb411(tb411)
                .goodsCd(form.getGoodsCd())
                .standard(form.getStandard())
                .qty(form.getQty())
                .price(form.getPrice())
                .amt(form.getAmt())
                .vat(form.getVat())
                .deadDt(deadDt)
                .note(form.getNote())
                .build();
    }
}
