package project.honey.produce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.produce.dto.Tb501Dto;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_501")
public class Tb501 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @Comment("생산공정코드")
    @Column(name = "productcd", columnDefinition = "char")
    private String productCd;

    @Comment("생산공정정렬순서")
    @Column(name = "productal")
    private Integer productAl;

    @Comment("생산공정명")
    @Column(name = "productnm")
    private String productNm;

    public void updateInfo(Tb501Dto dto) {
        this.productCd = dto.getProductCd();
        this.productAl = dto.getProductAl();
        this.productNm = dto.getProductNm();
    }
}
