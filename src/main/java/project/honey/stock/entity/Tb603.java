package project.honey.stock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.comm.GlobalMethod;
import project.honey.stock.dto.form.Tb601Form;
import project.honey.stock.dto.form.Tb603Form;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_603")
public class Tb603 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @NotNull
    @Comment("작업일")
    @Column(name = "whousedt", length = 10, columnDefinition = "char")
    private String wHouseDt;

    @NotNull
    @Comment("처리순번")
    @Column(name = "whouseno", length = 11)
    private Integer wHouseNo;

    @Comment("담당자")
    @Column(name = "empno", length = 10)
    private String empNo;

    @Comment("보내는창고")
    @Column(name = "whouseout", length = 5, columnDefinition = "char")
    private String wHouseOut;

    @NotNull
    @Comment("처리방법")
    @Column(name = "prcsmtd", length = 5, columnDefinition = "char")
    private String prcsmTd;


    @Comment("프로젝트")
    @Column(name = "projectcd", length = 5, columnDefinition = "char")
    private String projectCd;

    @Comment("적요")
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

    @OneToMany(mappedBy = "tb603", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Tb603_1> tb603_1s;

    // 컬렉션 채우기
    public void addList(List<Tb603_1> list) {
        tb603_1s.addAll(list);
    }

    public void updateData(Tb603Form dto, List<Tb603_1> tb603_1s) {
        String closeYn = dto.getCloseYn() != null ? dto.getCloseYn() : "N";

        this.wHouseDt = GlobalMethod.replaceYmd(dto.getWHouseDt(), "-");
        this.wHouseNo = dto.getWHouseNo();
        this.empNo = dto.getEmpNo();
        this.prcsmTd = dto.getPrcsmTd();
        this.wHouseOut = dto.getWHouseOut();
        this.projectCd = dto.getProjectCd();
        this.note = dto.getNote();
        this.status = dto.getStatus();
        this.closeYn = closeYn;

        // 컬렉션 교체
        this.tb603_1s.clear();
        this.tb603_1s.addAll(tb603_1s);
    }
}
