package project.honey.personDepart.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.personDepart.form.Tb201Form;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_201")
@ToString
public class Tb201 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("사원번호")
    @Column(name = "empno", length = 10)
    private String empNo;

    @NotNull
    @Comment("사원이름")
    @Column(name = "empnm", length = 30)
    private String empNm;

    @Comment("제2외국어성명")
    @Column(name = "emp2nm", length = 30)
    private String emp2Nm;

    @Comment("영문성명")
    @Column(name = "empengnm", length = 30)
    private String empEngNm;

    @Comment("주민등록번호")
    @Column(name = "idno", length = 20)
    private String idNo;

    @NotNull
    @Comment("세대주여부")
    @Column(name = "headyn", length = 2, columnDefinition = "char")
    private String headYn;

    @Comment("입사일자")
    @Column(name = "empdt", length = 10, columnDefinition = "char")
    private String empDt;

    @NotNull
    @Comment("입사구분")
    @Column(name = "empclass", length = 5, columnDefinition = "char")
    private String empClass;

    @Comment("직위/직급")
    @Column(length = 5, columnDefinition = "char")
    private String post;

    @Comment("직책")
    @Column(length = 5, columnDefinition = "char")
    private String post1;

    @Comment("퇴사일자")
    @Column(name = "rsntdt", length = 10, columnDefinition = "char")
    private String leaveDt;

    @Comment("퇴사사유")
    @Column(name = "rsntrs", length = 20)
    private String leaveRs;

    @Comment("전화번호")
    @Column(length = 20)
    private String phone;

    @Comment("모바일")
    @Column(length = 20)
    private String mobile;

    @Comment("여권번호")
    @Column(name = "psno", length = 20)
    private String psNo;

    @Comment("Email")
    @Column(length = 30)
    private String email;

    @Comment("부서코드")
    @Column(name = "deptcd", length = 5, columnDefinition = "char")
    private String deptCd;

    @Comment("업무코드")
    @Column(name = "workcd", length = 5, columnDefinition = "char")
    private String workCd;

    @Comment("급여은행")
    @Column(name = "banknm", length = 5, columnDefinition = "char")
    private String bankNm;

    @Comment("사원계좌번호")
    @Column(name = "acutno", length = 30)
    private String aCutNo;

    @Comment("예금주")
    @Column(name = "acutnm", length = 30)
    private String aCutNm;

    @Comment("우편번호")
    @Column(name = "zipcd", length = 10)
    private String zipCd;

    @Comment("주소")
    @Column(length = 50)
    private String address;

    @Comment("주소상세")
    @Column(length = 50)
    private String address1;

    @Comment("사진")
    @Column(name = "picnm", length = 50)
    private String picNm;

    @Comment("비고")
    @Column(length = 255)
    private String note;

    @Comment("파일")
    @Column(name = "filenm", length = 100)
    private String fileNm;

    public void updateData(Tb201Form form, String fileNm, String imgNm) {
        String headYn;
        if (form.getHeadYn()) headYn = "Y";
        else headYn = "N";
        this.seq      = form.getSeq();
        this.empNo    = form.getEmpNo();
        this.empNm    = form.getEmpNm();
        this.emp2Nm   = form.getEmp2Nm();
        this.empEngNm = form.getEmpEngNm();
        this.idNo     = form.getIdNo();
        this.headYn   = headYn;
        this.empDt    = form.getEmpDt();
        this.empClass = form.getEmpClass();
        this.post     = form.getPost();
        this.post1    = form.getPost1();
        this.leaveDt  = form.getLeaveDt();
        this.leaveRs  = form.getLeaveRs();
        this.phone    = form.getPhone();
        this.mobile   = form.getMobile();
        this.psNo     = form.getPsNo();
        this.email    = form.getEmail();
        this.deptCd   = form.getDeptCd();
        this.workCd   = form.getWorkCd();
        this.bankNm   = form.getBankNm();
        this.aCutNo   = form.getACutNo();
        this.aCutNm   = form.getACutNm();
        this.zipCd    = form.getZipCd();
        this.address  = form.getAddress();
        this.address1 = form.getAddress1();
        this.picNm    = imgNm;
        this.note     = form.getNote();
        this.fileNm   = fileNm;
    }
}