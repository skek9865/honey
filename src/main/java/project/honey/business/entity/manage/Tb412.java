package project.honey.business.entity.manage;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.business.form.manage.Tb412Form;
import project.honey.business.form.manage.Tb412_1Form;
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
@ToString(exclude = "tb412_1s")
@Table(name = "tb_412")
public class Tb412 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("판매일자")
    @Column(name = "saledt", length = 10, columnDefinition = "char")
    private String saleDt;

    @NotNull
    @Comment("판매번호")
    @Column(name = "saleno")
    private Integer saleNo;

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

    @Comment("출고자")
    @Column(name = "outnm", length = 50)
    private String outNm;

    @Comment("프로젝트")
    @Column(name = "projectcd", length = 5, columnDefinition = "char")
    private String projectCd;

    @Comment("배송우편번호")
    @Column(name = "zipcd", length = 10)
    private String zipCd;

    @Comment("배송주소")
    @Column(length = 50)
    private String address;

    @Comment("배송주소상세")
    @Column(length = 50)
    private String address1;

    @Comment("이름/연락처")
    @Column(length = 100)
    private String name;

    @Comment("인수증확인")
    @Column(name = "takeok", length = 50)
    private String takeOk;

    @Comment("기타사항")
    @Column(columnDefinition = "TEXT")
    private String note;

    @Comment("출고시참조")
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

    @OneToMany(mappedBy = "tb412", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Tb412_1> tb412_1s = new ArrayList<>();

    public void updateData(Tb412Form tb412Form, List<Tb412_1> tb412_1s){
        String closeYn = "N";
        String prtDt = "";
        String prtEmp = "";
        if(tb412Form.getCloseYn()) closeYn = "Y";
        if(tb412Form.getPrtDt() != null) prtDt = tb412Form.getPrtDt();
        if(tb412Form.getPrtEmp() != null) prtEmp = tb412Form.getPrtEmp();
        String saleDt = GlobalMethod.replaceYmd(tb412Form.getSaleDt(), "-");

        this.seq = tb412Form.getSeq();
        this.saleDt = saleDt;
        this.saleNo = tb412Form.getSaleNo();
        this.custCd = tb412Form.getCustCd();
        this.empNo = tb412Form.getEmpNo();
        this.whouseCd = tb412Form.getWhouseCd();
        this.saleType = tb412Form.getSaleType();
        this.excgCd = tb412Form.getExcgCd();
        this.outNm = tb412Form.getOutNm();
        this.projectCd = tb412Form.getProjectCd();
        this.zipCd = tb412Form.getZipCd();
        this.address = tb412Form.getAddress();
        this.address1 = tb412Form.getAddress1();
        this.name = tb412Form.getName();
        this.takeOk = tb412Form.getTakeOk();
        this.note = tb412Form.getNote();
        this.note2 = tb412Form.getNote2();
        this.status = tb412Form.getStatus();
        this.closeYn = closeYn;
        this.prtEmp = prtEmp;
        this.prtDt = prtDt;

        this.tb412_1s.clear();
        this.tb412_1s.addAll(tb412_1s);
    }
}
