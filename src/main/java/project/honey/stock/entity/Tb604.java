package project.honey.stock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.comm.GlobalMethod;
import project.honey.stock.dto.form.Tb601Form;
import project.honey.stock.dto.form.Tb604Form;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_604")
public class Tb604 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @NotNull
    @Comment("작업일")
    @Column(name = "whousedt", length = 10, columnDefinition = "char")
    private String wHouseDt;

    @NotNull
    @Comment("처리순번")
    @Column(name = "whouseno", length = 11)
    private Integer wHouseNo;

    @Comment("담당자")
    @Column(name = "empno", length = 10)
    private String empNo;

    @NotNull
    @Comment("창고")
    @Column(name = "whouseout", length = 5, columnDefinition = "char")
    private String wHouseOut;

    @NotNull
    @Comment("품목")
    @Column(name = "goodscd", length = 20, columnDefinition = "char")
    private String goodsCd;

    @NotNull
    @Comment("재고수량")
    @Column(name = "stqty", length = 11)
    private Integer stQty;

    @NotNull
    @Comment("실사수량")
    @Column(name = "reqty", length = 11)
    private Integer reQty;

    @NotNull
    @Comment("조정수량")
    @Column(name = "adqty", length = 11)
    private Integer adQty;

    @NotNull
    @Comment("적요")
    private String note;

    public void updateData(Tb604Form form) {
        this.wHouseDt = form.getWHouseDt();
        this.empNo = form.getEmpNo();
        this.wHouseOut = form.getWHouseOut();
        this.goodsCd = form.getGoodsCd();
        this.stQty = form.getStQty();
        this.reQty = form.getReQty();
        this.adQty = form.getAdQty();
        this.note = form.getNote();
    }
}
