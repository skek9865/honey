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
@ToString(exclude = "tb411")
@Table(name = "tb_411_1")
public class Tb411_1 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tb_411")
    @Comment("주문순번")
    private Tb411 tb411;

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

    @Comment("납기일자")
    @Column(name = "deaddt", length = 10, columnDefinition = "char")
    private String deadDt;

    @Comment("적요")
    private String note;
}
