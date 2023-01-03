package project.honey.company.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.comm.GlobalMethod;
import project.honey.company.form.Tb106_1Form;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@Table(name = "tb_106_1")
public class Tb106_1 extends BaseAtt {

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
    @Comment("법인자동차번호")
    @Column(name = "fk_tb_106")
    private Integer tb106;

    @NotNull
    @Comment("운행시작일자")
    @Column(name = "dvstdt", length = 10)
    private String dvStDt;

    @NotNull
    @Comment("운행종료일자")
    @Column(name = "dveddt", length = 10)
    private String dvEdDt;

    @NotNull
    @Comment("운행자")
    @Column(name = "empno", length = 10)
    private String empNo;

    @NotNull
    @Comment("금액")
    private Integer price;

    @Comment("과태료")
    private Integer penalty;

    @NotNull
    @Comment("출장지")
    @Column(length = 30)
    private String place;

    @NotNull
    @Comment("출장목적")
    @Column(length = 100)
    private String purpose;

    @Comment("비고")
    private String note;

    public void updateData(Tb106_1Form form){
        String dvStDt = GlobalMethod.replaceYmd(form.getDvStDt(), "-");
        String dvEdDt = GlobalMethod.replaceYmd(form.getDvEdDt(), "-");

        this.seq = form.getSeq();
        this.tb101 = form.getTb101();
        this.tb106 = form.getTb106();
        this.dvStDt = dvStDt;
        this.dvEdDt = dvEdDt;
        this.empNo = form.getEmpNo();
        this.price = form.getPrice();
        this.penalty = form.getPenalty();
        this.place = form.getPlace();
        this.purpose = form.getPurpose();
        this.note = form.getNote();
    }
}
