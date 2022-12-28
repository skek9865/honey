package project.honey.business.entity.sale;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.business.form.sale.Tb417Form;
import project.honey.comm.BaseAtt;
import project.honey.comm.GlobalMethod;
import project.honey.company.entity.Tb102;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_417")
@ToString(exclude = "tb102")
public class Tb417 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("입출금일자")
    @Column(name = "amountdt", length = 10, columnDefinition = "char")
    private String amountDt;

    @NotNull
    @Comment("입출금순번")
    @Column(name = "amountno")
    private Integer amountNo;

    @NotNull
    @Comment("입출/구분")
    @Column(name = "amountcl", length = 5, columnDefinition = "char")
    private String amountCl;

    @Comment("입출타입")
    @Column(name = "amountty", length = 5, columnDefinition = "char")
    private String amountTy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_xpr_326")
    @Comment("견적순번")
    private Tb102 tb102;

    @Comment("매출액")
    private Integer price;

    @NotNull
    @Comment("입출금액")
    private Integer amount;

    @NotNull
    @Comment("거래처코드")
    @Column(name = "custcd", length = 10, columnDefinition = "char")
    private String custCd;

    @Comment("사원번호")
    @Column(name = "empno", length = 10)
    private String empNo;

    @Comment("적요")
    @Column(columnDefinition = "TEXT")
    private String note;

    @Comment("참조")
    @Column(columnDefinition = "TEXT")
    private String note1;

    public void updateData(Tb417Form form, Tb102 tb102){
        String amountDt = GlobalMethod.replaceYmd(form.getAmountDt(), "-");

        this.seq = form.getSeq();
        this.amountDt = amountDt;
        this.amountNo = form.getAmountNo();
        this.amountCl = form.getAmountCl();
        this.amountTy = form.getAmountTy();
        this.tb102 = tb102;
        this.price = form.getPrice();
        this.amount = form.getAmount();
        this.custCd = form.getCustCd();
        this.empNo = form.getEmpNo();
        this.note = form.getNote();
        this.note1 = form.getNote1();
    }
}
