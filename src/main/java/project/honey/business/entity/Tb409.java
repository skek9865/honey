package project.honey.business.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.business.form.Tb409Form;
import project.honey.comm.BaseAtt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@Table(name = "tb_409")
public class Tb409 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("외화코드")
    @Column(name = "excgcd", length = 5, columnDefinition = "char")
    private String excgCd;

    @NotNull
    @Comment("외화코드명")
    @Column(name = "excgnm", length = 20)
    private String excgNm;

    @NotNull
    @Comment("환율")
    @Column(name = "excgrate", precision = 14, scale = 3)
    private BigDecimal excgRate;

    @NotNull
    @Comment("사용여부")
    @Column(name = "useyn", length = 2, columnDefinition = "char")
    private String useYn;

    public void updateData(Tb409Form form){
        String useYn = "N";
        if(form.getUseYn()) useYn = "Y";

        this.excgCd = form.getExcgCd();
        this.excgNm = form.getExcgNm();
        this.excgRate = form.getExcgRate();
        this.useYn = useYn;
    }
}
