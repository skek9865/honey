package project.honey.business.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.business.form.Tb403Form;
import project.honey.comm.BaseAtt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@Table(name = "tb_403")
public class Tb403 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("창고구분")
    @Column(name = "whousecla", length = 5, columnDefinition = "char")
    private String whouseCla;

    @Comment("생산공정코드")
    @Column(name = "productcd", length = 5, columnDefinition = "char")
    private String productCd;

    @NotNull
    @Comment("창고코드")
    @Column(name = "whousecd", length = 5, columnDefinition = "char")
    private String whouseCd;

    @NotNull
    @Comment("창고정렬순서")
    @Column(name = "whouseal", length = 11)
    private Integer whouseAl;

    @NotNull
    @Comment("창고명")
    @Column(name = "whousenm", length = 50)
    private String whouseNm;

    public void updateData(Tb403Form form){
        this.seq = form.getSeq();
        this.whouseCla = form.getWhouseCla();
        this.productCd = form.getProductCd();
        this.whouseCd = form.getWhouseCd();
        this.whouseAl = form.getWhouseAl();
        this.whouseNm = form.getWhouseNm();
    }
}

