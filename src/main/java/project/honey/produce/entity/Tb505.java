package project.honey.produce.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.comm.GlobalMethod;
import project.honey.produce.dto.form.Tb504Form;
import project.honey.produce.dto.form.Tb505Form;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_505")
public class Tb505 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @NotNull
    @Comment("입고일")
    @Column(name = "whousedt", length = 10, columnDefinition = "char")
    private String wHouseDt;

    @NotNull
    @Comment("입고순번")
    @Column(name = "whouseno", length = 11)
    private Integer wHouseNo;

    @NotNull
    @Comment("담당자")
    @Column(name = "empno", length = 10)
    private String empNo;

    @NotNull
    @Comment("보내는창고")
    @Column(name = "whouseout", length = 5, columnDefinition = "char")
    private String wHouseOut;

    @NotNull
    @Comment("받는창고")
    @Column(name = "whousein", length = 5, columnDefinition = "char")
    private String wHouseIn;


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

    @OneToMany(mappedBy = "tb505", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Tb505_1> tb505_1s;

    // 컬렉션 채우기
    public void addList(List<Tb505_1> list) {
        tb505_1s.addAll(list);
    }

    public void updateData(Tb505Form dto, List<Tb505_1> tb505_1s) {
        String closeYn = dto.getCloseYn() != null ? dto.getCloseYn() : "N";

        this.wHouseDt = GlobalMethod.replaceYmd(dto.getWHouseDt(), "-");
        this.wHouseNo = dto.getWHouseNo();
        this.empNo = dto.getEmpNo();
        this.wHouseIn = dto.getWHouseIn();
        this.wHouseOut = dto.getWHouseOut();
        this.projectCd = dto.getProjectCd();
        this.note = dto.getNote();
        this.status = dto.getStatus();
        this.closeYn = closeYn;


        // 컬렉션 교체
        this.tb505_1s.clear();
        this.tb505_1s.addAll(tb505_1s);
    }
}
