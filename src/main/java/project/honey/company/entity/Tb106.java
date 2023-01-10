package project.honey.company.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.comm.GlobalMethod;
import project.honey.company.form.Tb106Form;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@Table(name = "tb_106")
public class Tb106 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("회사코드")
    @Column(name = "fk_tb_101")
    private Integer tb101;

    @NotNull
    @Comment("자동차명")
    @Column(name = "carnm", length = 50)
    private String carNm;

    @Comment("연식")
    @Column(name = "caryear", length = 10)
    private String carYear;

    @Comment("차량번호")
    @Column(name = "carno", length = 20)
    private String carNo;

    @Comment("관리자")
    @Column(name = "empno", length = 30)
    private String empNo;

    @Comment("보험회사")
    @Column(name = "instnm", length = 50)
    private String instNm;

    @Comment("구입일")
    @Column(name = "buydt", length = 10)
    private String buyDt;

    @Comment("구입금액")
    @Column(name = "buyamt")
    private Integer buyAmt;

    @Comment("비고")
    private String note;

    public void updateData(Tb106Form form){
        String buyDt = GlobalMethod.replaceYmd(form.getBuyDt(), "-");

        this.seq = form.getSeq();
        this.tb101 = form.getTb101();
        this.carNm = form.getCarNm();
        this.carYear = form.getCarYear();
        this.carNo = form.getCarNo();
        this.empNo = form.getEmpNo();
        this.instNm = form.getInstNm();
        this.buyDt = buyDt;
        this.buyAmt = form.getBuyAmt();
        this.note = form.getNote();

    }
}
