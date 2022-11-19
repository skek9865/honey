package project.honey.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.system.dto.Tb901Dto;
import project.honey.system.dto.Tb906Dto;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_906")
public class Tb906 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @Comment("대그룹ID")
    @Column(name = "fstid", columnDefinition = "char")
    private String fstId;

    @Comment("중그룹ID")
    @Column(name = "scdid", columnDefinition = "char")
    private String scdId;

    @Comment("정렬순번")
    private Integer alien;

    @Comment("코드명")
    @Column(name = "codenm")
    private String codeNm;

    public void changeInfo(Tb906Dto dto) {
        this.fstId = dto.getFstId();
        this.scdId = dto.getScdId();
        this.alien = dto.getAlien();
        this.codeNm = dto.getCodeNm();
    }
}
