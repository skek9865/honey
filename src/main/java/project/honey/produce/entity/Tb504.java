package project.honey.produce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.comm.GlobalMethod;
import project.honey.produce.dto.form.Tb504Form;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_504")
public class Tb504 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @NotNull
    @Comment("작업일")
    @Column(name = "workdt", length = 10, columnDefinition = "char")
    private String workDt;

    @Comment("작업지시순번")
    @Column(name = "workdtno", length = 11)
    private Integer workDtNo;

    @NotNull
    @Comment("거래처코드")
    @Column(name = "custcd", length = 10, columnDefinition = "char")
    private String custCd;

    @Comment("생산공정코드")
    @Column(name = "productcd", length = 5, columnDefinition = "char")
    private String productCd;

    @Comment("담당자")
    @Column(name = "empno", length = 10)
    private String empNo;

    @Comment("납기일자")
    @Column(name = "deaddt", length = 10, columnDefinition = "char")
    private String deadDt;

    @Comment("특이사항")
    @Column(columnDefinition = "TEXT")
    private String note;

    @NotNull
    @Comment("상태")
    @Column(length = 5, columnDefinition = "char")
    private String status;

    @NotNull
    @Comment("종결여부")
    @Column(name="closeyn",length = 2, columnDefinition = "char")
    private String closeYn;

    @Comment("인쇄자")
    @Column(name="prtemp",length = 30)
    private String prtEmp;

    @Comment("인쇄일시")
    @Column(name="prtdt",length = 20, columnDefinition = "char")
    private String prtDt;

    @OneToMany(mappedBy = "tb504", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Tb504_1> tb504_1s;

    // 컬렉션 채우기
    public void addList(List<Tb504_1> list) {
        tb504_1s.addAll(list);
    }

    public void updateData(Tb504Form dto, List<Tb504_1> tb504_1s) {

        String closeYn = dto.getCloseYn() != null ? dto.getCloseYn() : "N";

        this.workDt = GlobalMethod.replaceYmd(dto.getWorkDt(), "-");
        this.workDtNo = dto.getWorkDtNo();
        this.custCd = dto.getCustCd();
        this.productCd = dto.getProductCd();
        this.empNo = dto.getEmpNo();
        this.deadDt = GlobalMethod.replaceYmd(dto.getDeadDt(), "-");
        this.note = dto.getNote();
        this.status = dto.getStatus();
        this.closeYn = closeYn;


        // 컬렉션 교체
        this.tb504_1s.clear();
        this.tb504_1s.addAll(tb504_1s);
    }
}
