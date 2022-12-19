package project.honey.business.entity.basic;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.business.form.basic.Tb405Form;
import project.honey.comm.BaseAtt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_405")
@ToString
public class Tb405 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("품목코드")
    @Column(name = "goodscd", length = 20, columnDefinition = "char")
    private String goodsCd;

    @NotNull
    @Comment("품목구분명")
    @Column(name = "goodsnm", length = 50)
    private String goodsNm;

    @Comment("규격")
    @Column(length = 20)
    private String standard;

    @Comment("단위")
    @Column(length = 20)
    private String unit;

    @NotNull
    @Comment("품목구분")
    @Column(name = "class", length = 5, columnDefinition = "char")
    private String classSeq;

    @NotNull
    @Comment("세트여부")
    @Column(name = "setyn", length = 2, columnDefinition = "char")
    private String setYn;

    @NotNull
    @Comment("재고수량관리")
    @Column(name = "stockyn", length = 5, columnDefinition = "char")
    private String stockYn;

    @Comment("생산공정")
    @Column(length = 5, columnDefinition = "char")
    private String product;

    @Comment("품목그룹1")
    @Column(name="itemgb1", length = 5, columnDefinition = "char")
    private String itemGb1;

    @Comment("품목그룹2")
    @Column(name="itemgb2", length = 5, columnDefinition = "char")
    private String itemGb2;

    @NotNull
    @Comment("재고수량")
    @Column(name="stockqty", length = 11, columnDefinition = "float")
    private Integer stockQty;

    @Comment("안전재고")
    @Column(name="aqty", length = 11, columnDefinition = "int")
    private Integer aQty;

    @NotNull
    @Comment("입고단가")
    @Column(name="wprice", length = 11, columnDefinition = "float")
    private Integer wPrice;

    @NotNull
    @Comment("입고 VAT")
    @Column(name="wpricevat", length = 2, columnDefinition = "char")
    private String wPriceVat;

    @NotNull
    @Comment("출고단가")
    @Column(name="fprice", length = 11, columnDefinition = "float")
    private Integer fPrice;

    @NotNull
    @Comment("출고 VAT")
    @Column(name="fpricevat", length = 2, columnDefinition = "char")
    private String fPriceVat;

    @Comment("상품이미지저장명")
    @Column(name = "imgnmsave", length = 50)
    private String imgNmSave;

    @Comment("상품이미지출력명")
    @Column(name = "imgnmout", length = 50)
    private String imgNmOut;

    public void changeImgNmSave(Integer seq, String ext) {
        this.imgNmSave = seq + "." + ext;
    }

    public void updateData(Tb405Form form, String imgNmOut) {
        this.goodsCd = form.getGoodsCd();
        this.goodsNm = form.getGoodsNm();
        this.standard = form.getStandard();
        this.unit = form.getUnit();
        this.classSeq = form.getClassSeq();
        this.setYn = form.getSetYn();
        this.stockYn = form.getStockYn();
        this.product = form.getProduct();
        this.itemGb1 = form.getItemGb1();
        this.itemGb2 = form.getItemGb2();
        this.stockQty = form.getStockQty();
        this.aQty = form.getAQty();
        this.wPrice = form.getWPrice();
        this.wPriceVat = form.getWPriceVat();
        this.fPrice = form.getFPrice();
        this.fPriceVat = form.getFPriceVat();
        this.imgNmOut = imgNmOut;
    }
}
