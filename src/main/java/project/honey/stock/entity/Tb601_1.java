package project.honey.stock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.produce.entity.Tb505;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_601_1")
public class Tb601_1 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @Comment("창고이동순번")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tb_601")
    private Tb601 tb601;

    @NotNull
    @Comment("품목")
    @Column(name = "goodscd", length = 20, columnDefinition = "char")
    private String goodsCd;

    @NotNull
    @Comment("수량")
    @Column(length = 11)
    private Integer qty;

    @NotNull
    @Comment("받는창고재고수량")
    @Column(name = "rqty", length = 11)
    private Integer rQty;

    @Comment("적요")
    private String note;

}
