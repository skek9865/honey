package project.honey.produce.entity;

import lombok.*;
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
@Table(name = "tb_504_1")
public class Tb504_1 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @Comment("작업지시서순번")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tb_504")
    private Tb504 tb504;

    @NotNull
    @Comment("생산품목")
    @Column(name = "goodscd", length = 20, columnDefinition = "char")
    private String goodsCd;

    @NotNull
    @Comment("목표수량")
    @Column(length = 11)
    private Integer qty;

    @NotNull
    @Comment("생산수량")
    @Column(name="rqty", length = 11)
    private Integer rQty;

    @NotNull
    @Comment("상태")
    @Column(length = 5, columnDefinition = "char")
    private String status;

    @Comment("호기")
    @Column(name = "machineno", length = 10)
    private String machineNo;

    @Comment("작업자")
    @Column(name = "empno", length = 10)
    private String empNo;

    @Comment("특이사항")
    private String note;

    @Comment("검수수량")
    @Column(name="qqty", length = 11)
    private Integer qQty;

    @Comment("남은검수수량")
    @Column(name="qqtyn", length = 11)
    private Integer qQtyN;

    @Comment("규격")
    @Column(length = 20)
    private String standard;

    @Comment("검수상태")
    @Column(length = 5, columnDefinition = "char")
    private String statusQ;

    @Comment("검수사원")
    @Column(name = "empnoq", length = 10)
    private String empNoQ;

    @Comment("특이사항")
    @Column(name = "noteq")
    private String noteQ;

    @OneToMany(mappedBy = "tb504_1", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Tb504_2> tb504_2s;

    public void changeTb504_2s(List<Tb504_2> list) {
        this.tb504_2s.clear();
        tb504_2s.addAll(list);
    }
}
