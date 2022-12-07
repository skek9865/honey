package project.honey.produce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.produce.dto.input.Tb503Input;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_503")
public class Tb503 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @NotNull
    @Comment("생산품목")
    @Column(name = "goodscd", length = 20, columnDefinition = "char")
    private String goodsCd;

    @Comment("생산공정코드")
    @Column(name = "productcd", length = 5, columnDefinition = "char")
    private String productCd;

    @Comment("생산수량")
    @Column(length = 11, columnDefinition = "int")
    private Integer qty;

    @OneToMany(mappedBy = "tb503", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Tb503_1> tb503_1s;

    // 컬렉션 채우기
    public void addList(List<Tb503_1> list) {
        tb503_1s.addAll(list);
    }

    public void updateData(Tb503Input dto, List<Tb503_1> tb503_1s) {
        this.goodsCd = dto.getGoodsCd();
        this.productCd = dto.getProductCd();
        this.qty = dto.getQty();
        // 컬렉션 교체
        this.tb503_1s.clear();
        this.tb503_1s.addAll(tb503_1s);
    }
}
