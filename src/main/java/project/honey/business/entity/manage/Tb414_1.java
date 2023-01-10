package project.honey.business.entity.manage;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString(exclude = "tb414")
@Table(name = "tb_414_1")
public class Tb414_1 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tb_414")
    @Comment("출하순번")
    private Tb414 tb414;

    @NotNull
    @Comment("품목코드")
    @Column(name = "goodscd", length = 20, columnDefinition = "char")
    private String goodsCd;

    @Comment("규격")
    @Column(length = 20)
    private String standard;

    @NotNull
    @Comment("수량")
    private Integer qty;

    @Comment("적요")
    private String note;

    @Comment("대 QR코드")
    @Column(name = "qrcode")
    private String qrCode;

    @Comment("중 QR코드")
    @Column(name = "qrcode1")
    private String qrCode1;

    @Comment("소 QR코드")
    @Column(name = "qrcode2")
    private String qrCode2;
}
