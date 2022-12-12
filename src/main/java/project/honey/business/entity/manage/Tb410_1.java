package project.honey.business.entity.manage;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@Table(name = "tb_410_1")
public class Tb410_1 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("견적순번")
    private Tb410 fk_tb_410;

    @NotNull
    @Comment("품목코드")
    @Column(name = "goodscd", length = 5, columnDefinition = "char")
    private String goodsCd;

    @Comment("규격")
    @Column(length = 20)
    private String standard;

    @NotNull
    @Comment("수량")
    private Integer qty;

    @NotNull
    @Comment("단가")
    private BigDecimal price;

    @NotNull
    @Comment("공급가액")
    private BigDecimal amt;

    @NotNull
    @Comment("부가세")
    private BigDecimal vat;

    @Comment("적요")
    @Column(length = 255)
    private String note;
}
