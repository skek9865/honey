package project.honey.business.entity.manage;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.business.form.manage.Tb410Form;
import project.honey.comm.BaseAtt;
import project.honey.comm.GlobalMethod;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString(exclude = "tb410_1s")
@Table(name = "tb_410")
public class Tb410 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("일자")
    @Column(name = "estimdt", length = 10, columnDefinition = "char")
    private String estimDt;

    @NotNull
    @Comment("견적번호")
    @Column(name = "estimno", length = 11)
    private Integer estimNo;

    @NotNull
    @Comment("거래처코드")
    @Column(name = "custcd", length = 10, columnDefinition = "char")
    private String custCd;

    @NotNull
    @Comment("담당자")
    @Column(name = "empno", length = 10)
    private String empNo;

    @NotNull
    @Comment("출하창고")
    @Column(name = "whousecd", length = 5, columnDefinition = "char")
    private String whouseCd;

    @NotNull
    @Comment("거래유형")
    @Column(name = "saletype", length = 5, columnDefinition = "char")
    private String saleType;

    @NotNull
    @Comment("통화")
    @Column(name = "excgcd", length = 5, columnDefinition = "char")
    private String excgCd;

    @Comment("참조")
    @Column(length = 255)
    private String note;

    @Comment("결제조건")
    @Column(name = "paycondit", length = 255)
    private String payCondit;

    @Comment("성명")
    @Column(length = 50)
    private String name;

    @NotNull
    @Comment("유효기간")
    @Column(name = "expdt", length = 10, columnDefinition = "char")
    private String expDt;

    @Comment("TEL")
    @Column(length = 20)
    private String phone;

    @Comment("이메일")
    @Column(length = 30)
    private String email;

    @Comment("장문형식1")
    @Column(columnDefinition = "TEXT")
    private String note2;

    @NotNull
    @Comment("상태")
    @Column(length = 5, columnDefinition = "char")
    private String status;

    @NotNull
    @Comment("종결여부")
    @Column(name = "closeyn", length = 2, columnDefinition = "char")
    private String closeYn;

    @Comment("인쇄자")
    @Column(name = "prtemp", length = 30)
    private String prtEmp;

    @Comment("인쇄일시")
    @Column(name = "prtdt", length = 20, columnDefinition = "char")
    private String prtDt;

    @OneToMany(mappedBy = "tb410", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Tb410_1> tb410_1s = new ArrayList<>();

    public void updateData(Tb410Form form, List<Tb410_1> tb410_1) {
        String estDt = GlobalMethod.replaceYmd(form.getEstimDt(),"-");
        String closeYn = "N";
        if(form.getCloseYn()) closeYn = "Y";
        this.seq = form.getSeqP();
        this.estimDt = estDt;
        this.estimNo = form.getEstimNo();
        this.custCd = form.getCustCd();
        this.empNo = form.getEmpNo();
        this.whouseCd = form.getWhouseCd();
        this.saleType = form.getSaleType();
        this.excgCd = form.getExcgCd();
        this.note = form.getNoteP();
        this.payCondit = form.getPayCondit();
        this.name = form.getName();
        this.expDt = form.getExpDt();
        this.phone = form.getPhone();
        this.email = form.getEmail();
        this.note2 = form.getNote2();
        this.status = form.getStatus();
        this.closeYn = closeYn;
        this.prtEmp = form.getPrtEmp();
        this.prtDt = form.getPrtDt();

        this.tb410_1s.clear();
        this.tb410_1s.addAll(tb410_1);
    }
}
