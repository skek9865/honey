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
@Table(name = "tb_504_2")
public class Tb504_2 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @Comment("작업지시서상세순번")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tb_504_1")
    private Tb504_1 tb504_1;

    @NotNull
    @Comment("작업일")
    @Column(name = "workdt", length = 10, columnDefinition = "char")
    private String workDt;

    @Comment("작업지시순번")
    @Column(name = "workdtno", length = 11)
    private Integer workDtNo;

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

    @NotNull
    @Comment("작업자")
    @Column(name = "empno", length = 10)
    private String empNo;

    @Comment("특이사항")
    private String note;

}
