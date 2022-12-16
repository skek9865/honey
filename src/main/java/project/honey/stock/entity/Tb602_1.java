package project.honey.stock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_602_1")
public class Tb602_1 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @Comment("자가사용순번")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tb_602")
    private Tb602 tb602;

    @NotNull
    @Comment("품목")
    @Column(name = "goodscd", length = 20, columnDefinition = "char")
    private String goodsCd;

    @NotNull
    @Comment("수량")
    @Column(length = 11)
    private Integer qty;

    @Comment("사용유형")
    @Column(name = "usetype")
    private String useType;

    @Comment("적요")
    private String note;

}
