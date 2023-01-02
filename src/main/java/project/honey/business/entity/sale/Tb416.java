package project.honey.business.entity.sale;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.business.form.sale.Tb416Form;
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
@ToString(exclude = "tb416_1s")
@Table(name = "tb_416")
public class Tb416 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("구매일자")
    @Column(name = "buydt", length = 10, columnDefinition = "char")
    private String buyDt;

    @NotNull
    @Comment("구매번호")
    @Column(name = "buyno")
    private Integer buyNo;

    @NotNull
    @Comment("거래처코드")
    @Column(name = "custcd", length = 10, columnDefinition = "char")
    private String custCd;

    @NotNull
    @Comment("담당자")
    @Column(name = "empno", length = 10)
    private String empNo;

    @NotNull
    @Comment("입고창고")
    @Column(name = "whousecd", length = 5, columnDefinition = "char")
    private String whouseCd;

    @Comment("거래유형")
    @Column(name = "saletype", length = 5, columnDefinition = "char")
    private String saleType;

    @Comment("통화")
    @Column(name = "excgcd", length = 5, columnDefinition = "char")
    private String excgCd;

    @Comment("기타사항")
    @Column(columnDefinition = "TEXT")
    private String note;

    @Comment("프로젝트")
    @Column(name = "projectcd", length = 5, columnDefinition = "char")
    private String projectCd;

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

    @OneToMany(mappedBy = "tb416", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Tb416_1> tb416_1s = new ArrayList<>();

    public void updateData(Tb416Form form, List<Tb416_1> tb416_1s){
        String closeYn = "N";
        String prtDt = "";
        String prtEmp = "";
        if(form.getCloseYn()) closeYn = "Y";
        if(form.getPrtDt() != null) prtDt = form.getPrtDt();
        if(form.getPrtEmp() != null) prtEmp = form.getPrtEmp();
        String buyDt = GlobalMethod.replaceYmd(form.getBuyDt(), "-");

        this.seq = form.getSeq();
        this.buyDt = buyDt;
        this.buyNo = form.getBuyNo();
        this.custCd = form.getCustCd();
        this.empNo = form.getEmpNo();
        this.whouseCd = form.getWhouseCd();
        this.saleType = form.getSaleType();
        this.excgCd = form.getExcgCd();
        this.note = form.getNote();
        this.projectCd = form.getProjectCd();
        this.status = form.getStatus();
        this.closeYn = closeYn;
        this.prtEmp = prtEmp;
        this.prtDt = prtDt;

        this.tb416_1s.clear();
        this.tb416_1s.addAll(tb416_1s);
    }
}
