package project.honey.business.entity.manage;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.business.form.manage.Tb414Form;
import project.honey.business.form.manage.Tb414_1Form;
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
@ToString(exclude = "tb414_1s")
@Table(name = "tb_414")
public class Tb414 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("출하일자")
    @Column(name = "shipdt", length = 10, columnDefinition = "char")
    private String shipDt;

    @NotNull
    @Comment("출하번호")
    @Column(name = "shipno")
    private Integer shipNo;

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

    @Comment("프로젝트")
    @Column(name = "projectcd", length = 5, columnDefinition = "char")
    private String projectCd;

    @Comment("연락처")
    @Column(length = 20)
    private String phone;

    @Comment("우편번호")
    @Column(name = "zipcd", length = 10)
    private String zipCd;

    @Comment("주소")
    @Column(length = 50)
    private String address;

    @Comment("주소상세")
    @Column(length = 50)
    private String address1;

    @NotNull
    @Comment("적요")
    @Column(columnDefinition = "TEXT")
    private String note;

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

    @OneToMany(mappedBy = "tb414", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Tb414_1> tb414_1s = new ArrayList<>();

    public void updateData(Tb414Form form, List<Tb414_1> tb414_1s){
        String closeYn = "N";
        String prtDt = "";
        String prtEmp = "";
        if(form.getCloseYn()) closeYn = "Y";
        if(form.getPrtDt() != null) prtDt = form.getPrtDt();
        if(form.getPrtEmp() != null) prtEmp = form.getPrtEmp();
        String shipDt = GlobalMethod.replaceYmd(form.getShipDt(), "-");

        this.seq = form.getSeq();
        this.shipDt = shipDt;
        this.shipNo = form.getShipNo();
        this.custCd = form.getCustCd();
        this.empNo = form.getEmpNo();
        this.whouseCd = form.getWhouseCd();
        this.projectCd = form.getProjectCd();
        this.phone = form.getPhone();
        this.zipCd = form.getZipCd();
        this.address = form.getAddress();
        this.address1 = form.getAddress1();
        this.note = form.getNote();
        this.status = form.getStatus();
        this.closeYn = closeYn;
        this.prtEmp = prtEmp;
        this.prtDt = prtDt;

        this.tb414_1s.clear();
        this.tb414_1s.addAll(tb414_1s);
    }
}
