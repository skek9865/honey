package project.honey.business.form.manage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import project.honey.business.entity.manage.Tb410;
import project.honey.business.entity.manage.Tb410_1;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Tb410_1Form {

    private Integer seqC;
    private Integer tb410;
    private String goodsCd;
    private String standard;
    private Integer qty;
    private BigDecimal price;
    private BigDecimal amt;
    private BigDecimal vat;
    private String noteC;

    public static Tb410_1 toTb410_1(Tb410_1Form form){
        Tb410 tb410 = Tb410.builder().seq(form.getSeqC()).build();

        return Tb410_1.builder()
                .fk_tb_410(tb410)
                .goodsCd(form.getGoodsCd())
                .standard(form.getStandard())
                .qty(form.getQty())
                .price(form.getPrice())
                .amt(form.getAmt())
                .vat(form.getVat())
                .note(form.getNoteC())
                .build();
    }
}
