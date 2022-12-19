package project.honey.produce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_505_1")
public class Tb505_1 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @Comment("생산불출순번")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tb_505")
    private Tb505 tb505;

    @NotNull
    @Comment("생산품목")
    @Column(name = "goodscd", length = 20, columnDefinition = "char")
    private String goodsCd;

    @NotNull
    @Comment("생산수량")
    @Column(length = 11)
    private Integer qty;

    @NotNull
    @Comment("재고수량")
    @Column(name = "oqty", length = 11)
    private Integer oQty;

    @Comment("적요")
    private String note;

}
