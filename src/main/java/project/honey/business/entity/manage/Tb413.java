package project.honey.business.entity.manage;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.business.form.manage.Tb413Form;
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
@ToString(exclude = "tb413_1s")
@Table(name = "tb_413")
public class Tb413 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("출하지시서일자")
    @Column(name = "shipdt", length = 10, columnDefinition = "char")
    private String shipDt;

    @NotNull
    @Comment("출하지시서번호")
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

    @Comment("출하창고")
    @Column(name = "whousecd", length = 5, columnDefinition = "char")
    private String whouseCd;

    @Comment("프로젝트")
    @Column(name = "projectcd", length = 5, columnDefinition = "char")
    private String projectCd;

    @Comment("연락처")
    @Column(length = 20)
    private String phone;

    @NotNull
    @Comment("출하예정일")
    @Column(name = "outdt", length = 10, columnDefinition = "char")
    private String outDt;

    @Comment("우편번호1")
    @Column(name = "zipcd1", length = 10)
    private String zipCd1;

    @Comment("주소1")
    @Column(length = 50)
    private String address1;

    @Comment("주소상세1")
    @Column(length = 50)
    private String address11;

    @Comment("출고자")
    @Column(name = "shipnm", length = 20)
    private String shipNm;

    @Comment("택배발송자")
    @Column(length = 20)
    private String sender;

    @Comment("배송우편번호")
    @Column(name = "zipcd", length = 10)
    private String zipCd;

    @Comment("배송주소2")
    @Column(length = 50)
    private String address2;

    @Comment("배송주소상세2")
    @Column(length = 50)
    private String address21;

    @Comment("이름/연락처")
    @Column(length = 100)
    private String name;

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

    @OneToMany(mappedBy = "tb413", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Tb413_1> tb413_1s = new ArrayList<>();

    public void updateData(Tb413Form form, List<Tb413_1> tb413_1s){
        String closeYn = "N";
        String prtDt = "";
        String prtEmp = "";
        if(form.getCloseYn()) closeYn = "Y";
        if(form.getPrtDt() != null) prtDt = form.getPrtDt();
        if(form.getPrtEmp() != null) prtEmp = form.getPrtEmp();
        String shipDt = GlobalMethod.replaceYmd(form.getShipDt(), "-");
        String outDt = GlobalMethod.replaceYmd(form.getOutDt(), "-");

        this.seq = form.getSeq();
        this.shipDt = shipDt;
        this.shipNo = form.getShipNo();
        this.custCd = form.getCustCd();
        this.empNo = form.getEmpNo();
        this.whouseCd = form.getWhouseCd();
        this.projectCd = form.getProjectCd();
        this.phone = form.getPhone();
        this.outDt = outDt;
        this.zipCd1 = form.getZipCd1();
        this.address1 = form.getAddress1();
        this.address11 = form.getAddress11();
        this.shipNm = form.getShipNm();
        this.sender = form.getSender();
        this.zipCd = form.getZipCd();
        this.address2 = form.getAddress2();
        this.address21 = form.getAddress21();
        this.name = form.getName();
        this.note = form.getNote();
        this.note2 = form.getNote2();
        this.status = form.getStatus();
        this.closeYn = closeYn;
        this.prtEmp = prtEmp;
        this.prtDt = prtDt;

        this.tb413_1s.clear();
        this.tb413_1s.addAll(tb413_1s);
    }
}
