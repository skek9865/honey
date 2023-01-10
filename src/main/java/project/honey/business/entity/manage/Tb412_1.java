package project.honey.business.entity.manage;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString(exclude = "tb412")
@Table(name = "tb_412_1")
public class Tb412_1 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tb_412")
    @Comment("주문순번")
    private Tb412 tb412;

    @NotNull
    @Comment("품목코드")
    @Column(name = "goodscd", length = 20, columnDefinition = "char")
    private String goodsCd;

    @Comment("규격")
    @Column(length = 20)
    private String standard;

    @NotNull
    @Comment("수량")
    private Integer qty;

    @NotNull
    @Comment("단가")
    private Integer price;

    @NotNull
    @Comment("공급가액")
    private Integer amt;

    @NotNull
    @Comment("부가세")
    private Integer vat;

    @NotNull
    @Comment("합계")
    @Column(name = "amtsum")
    private Integer amtSum;

    @Comment("적요")
    private String note;

    @NotNull
    @Comment("단가(VAT포함)")
    @Column(name = "pricevat")
    private Integer priceVat;

    @NotNull
    @Comment("출하지시서작성")
    @Column(name = "shipyn", length = 2, columnDefinition = "char")
    private String shipYn;
}
