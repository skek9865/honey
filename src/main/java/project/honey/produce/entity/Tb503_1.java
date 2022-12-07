package project.honey.produce.entity;

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
@Table(name = "tb_503_1")
public class Tb503_1 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @Comment("bom순번")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tb_503")
    private Tb503 tb503;

    @NotNull
    @Comment("생산품목")
    @Column(name = "goodscd", length = 20, columnDefinition = "char")
    private String goodsCd;

    @NotNull
    @Comment("생산공정코드")
    @Column(name = "productcd", length =  5, columnDefinition = "char")
    private String productCd;

    @NotNull
    @Comment("생산수량")
    @Column(length = 11, columnDefinition = "int")
    private Integer qty;

    @Comment("위치")
    private String location;

    @Comment("적요")
    private String note;
}
