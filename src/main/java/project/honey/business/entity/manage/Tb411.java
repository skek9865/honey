package project.honey.business.entity.manage;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.business.form.manage.Tb411Form;
import project.honey.business.form.manage.Tb411_1Form;
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
@ToString(exclude = "tb411_1s")
@Table(name = "tb_411")
public class Tb411 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("주문일자")
    @Column(name = "orderdt", length = 10, columnDefinition = "char")
    private String orderDt;

    @NotNull
    @Comment("주문번호")
    @Column(name = "orderno", length = 11)
    private Integer orderNo;

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

    @Comment("프로젝트")
    @Column(name = "projectcd", length = 5, columnDefinition = "char")
    private String projectCd;

    @Comment("참조")
    private String note;

    @Comment("결제조건")
    @Column(name = "paycondit")
    private String payCondit;

    @NotNull
    @Comment("유효기간")
    @Column(name = "expdt", length = 10, columnDefinition = "char")
    private String expDt;

    @NotNull
    @Comment("납기일자")
    @Column(name = "deaddt", length = 10, columnDefinition = "char")
    private String deadDt;

    @Comment("기타사항")
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

    @NotNull
    @Comment("인쇄자")
    @Column(name = "prtemp", length = 30)
    private String prtEmp;

    @NotNull
    @Comment("인쇄일시")
    @Column(name = "prtdt", length = 20, columnDefinition = "char")
    private String prtDt;

    @OneToMany(mappedBy = "tb411", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Tb411_1> tb411_1s = new ArrayList<>();

    public void updateData(Tb411Form tb411Form, List<Tb411_1> tb411_1){
        String closeYn = "N";
        String prtDt = "";
        String prtEmp = "";
        if(tb411Form.getCloseYn()) closeYn = "Y";
        if(tb411Form.getPrtDt() != null) prtDt = tb411Form.getPrtDt();
        if(tb411Form.getPrtEmp() != null) prtEmp = tb411Form.getPrtEmp();
        String orderDt = GlobalMethod.replaceYmd(tb411Form.getOrderDt(), "-");
        String deadDt = GlobalMethod.replaceYmd(tb411Form.getDeadDt(), "-");

        this.seq = tb411Form.getSeq();
        this.orderDt = orderDt;
        this.orderNo = tb411Form.getOrderNo();
        this.custCd = tb411Form.getCustCd();
        this.empNo = tb411Form.getEmpNo();
        this.whouseCd = tb411Form.getWhouseCd();
        this.saleType = tb411Form.getSaleType();
        this.excgCd = tb411Form.getExcgCd();
        this.projectCd = tb411Form.getProjectCd();
        this.note = tb411Form.getNote();
        this.payCondit = tb411Form.getPayCondit();
        this.expDt = tb411Form.getExpDt();
        this.deadDt = deadDt;
        this.note2 = tb411Form.getNote2();
        this.status = tb411Form.getStatus();
        this.closeYn = closeYn;
        this.prtEmp = prtEmp;
        this.prtDt = prtDt;

        this.tb411_1s.clear();
        this.tb411_1s.addAll(tb411_1);
    }
}
