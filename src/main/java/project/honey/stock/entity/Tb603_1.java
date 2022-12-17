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
@Table(name = "tb_603_1")
public class Tb603_1 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @Comment("불량처리순번")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tb_603")
    private Tb603 tb603;

    @NotNull
    @Comment("품목")
    @Column(name = "goodscd", length = 20, columnDefinition = "char")
    private String goodsCd;

    @NotNull
    @Comment("수량")
    @Column(length = 11)
    private Integer qty;

    @Comment("대체품목")
    @Column(name = "goodscds", length = 20, columnDefinition = "char")
    private String goodsCds;

    @NotNull
    @Comment("정상수량")
    @Column(name = "rqty", length = 11)
    private Integer rQty;

    @Comment("불량유형")
    @Column(name = "faulytype",length = 5, columnDefinition = "char")
    private String faulyType;

    @Comment("불량유형")
    @Column(length = 50)
    private String color;

    @Comment("적요")
    private String note;

}
