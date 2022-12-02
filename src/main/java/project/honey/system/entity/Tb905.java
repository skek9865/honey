package project.honey.system.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.system.dto.Tb903Dto;
import project.honey.system.dto.Tb905Dto;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_905")
@ToString
public class Tb905 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Comment("메뉴그룹코드")
    @Column(name="menugbcd", columnDefinition = "char")
    private String menuGbCd;

    @Comment("메뉴그룹")
    @Column(name="menugbnm")
    private String menuGbNm;

    @Comment("화면ID")
    @Column(name="tpid")
    private String tpId;

    @Comment("접근여부")
    @Column(name = "menuyn", columnDefinition = "char")
    private String menuYn;

    @Comment("목록사용여부")
    @Column(name = "listyn", columnDefinition = "char")
    private String listYn;

    @Comment("건별조회사용여부")
    @Column(name = "viewyn", columnDefinition = "char")
    private String viewYn;

    @Comment("저장사용여부")
    @Column(name = "saveyn", columnDefinition = "char")
    private String saveYn;

    @Comment("수정사용여부")
    @Column(name = "modifyyn", columnDefinition = "char")
    private String modifyYn;

    @Comment("삭제사용여부")
    @Column(name = "delyn", columnDefinition = "char")
    private String delYn;

    public void changeInfo(Tb905Dto dto, Tb904 tb904) {
        this.menuGbCd = dto.getMenuGbCd();
        this.tpId = tb904.getFstId() + tb904.getScdId() + tb904.getThdId();
        this.menuYn = dto.getMenuYn();
        this.listYn = dto.getListYn();
        this.viewYn = dto.getViewYn();
        this.saveYn = dto.getSaveYn();
        this.modifyYn = dto.getModifyYn();
        this.delYn = dto.getDelYn();
    }
}
