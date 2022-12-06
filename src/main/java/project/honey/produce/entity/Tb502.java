package project.honey.produce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.produce.dto.Tb501Dto;
import project.honey.produce.dto.Tb502Dto;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_502")
public class Tb502 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @Comment("불량코드")
    @Column(name = "faulycd", columnDefinition = "char")
    private String faulyCd;

    @Comment("불량명")
    @Column(name = "faulynm")
    private String faulyNm;

    @Comment("적요")
    private String note;

    @Comment("불량정렬순번")
    @Column(name = "erroral", columnDefinition = "int")
    private Integer errorAl;

    @Comment("사용여부")
    @Column(name = "useyn", columnDefinition = "char")
    private String useYn;

    public void updateInfo(Tb502Dto dto) {
        this.faulyCd = dto.getFaulyCd();
        this.faulyNm = dto.getFaulyNm();
        this.note = dto.getNote();
        this.errorAl = dto.getErrorAl();
        this.useYn = dto.getUseYn();
    }
}
