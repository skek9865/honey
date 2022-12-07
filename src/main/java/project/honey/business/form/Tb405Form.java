package project.honey.business.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import project.honey.business.dto.Tb405Dto;
import project.honey.business.entity.Tb405;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Tb405Form {

    private Integer seq;
    private String goodsCd;
    private String goodsNm;
    private String standard;
    private String unit;
    private String classSeq;
    private String setYn;
    private String stockYn;
    private String product;
    private String itemGb1;
    private String itemGb2;
    private Integer stockQty;
    private Integer aQty;
    private Integer wPrice;
    private String wPriceVat;
    private Integer fPrice;
    private String fPriceVat;
    private MultipartFile img;

    public void setSetYn(String setYn) {
        this.setYn = setYn != null ? setYn : "N";
    }

    public void setStockYn(String stockYn) {
        this.stockYn = stockYn != null ? stockYn : "N";
    }
    public void setwPriceVat(String wPriceVat) {
        this.wPriceVat = wPriceVat != null ? wPriceVat : "N";
    }
    public void setfPriceVat(String fPriceVat) {
        this.fPriceVat = fPriceVat != null ? fPriceVat : "N";
    }

    public static Tb405 toTb405(Tb405Form form, String saveNm, String outNm) {

        return Tb405.builder()
                .seq(form.getSeq())
                .goodsCd(form.getGoodsCd())
                .goodsNm(form.getGoodsNm())
                .standard(form.getStandard())
                .unit(form.getUnit())
                .classSeq(form.getClassSeq())
                .setYn(form.getSetYn())
                .stockYn(form.getStockYn())
                .product(form.getProduct())
                .itemGb1(form.getItemGb1())
                .itemGb2(form.getItemGb2())
                .stockQty(form.getStockQty())
                .aQty(form.getAQty())
                .wPrice(form.getWPrice())
                .wPriceVat(form.getWPriceVat())
                .fPrice(form.getFPrice())
                .fPriceVat(form.getFPriceVat())
                .imgNmSave(saveNm)
                .imgNmOut(outNm)
                .build();
    }
}
