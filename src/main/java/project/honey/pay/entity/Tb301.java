package project.honey.pay.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.pay.dto.Tb301Dto;
import project.honey.system.dto.Tb901Dto;
import project.honey.system.dto.Tb906Dto;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_301")
public class Tb301 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @Comment("공제/지급구분")
    @Column(name = "itemdiv", columnDefinition = "char")
    private String itemDiv;

    @Comment("과세여부")
    @Column(name = "taxdiv", columnDefinition = "char")
    @ColumnDefault("N")
    private String taxDiv;

    @Comment("항목코드")
    @Column(name = "itemcd", columnDefinition = "char")
    private Integer itemCd;

    @Comment("항목명")
    @Column(name = "itemnm")
    private String itemNm;

    @Comment("세제율")
    @Column(name = "taxrate", columnDefinition = "float")
    private Double taxRate;

    @Comment("사용여부")
    @Column(name = "useyn", columnDefinition = "char")
    @ColumnDefault("N")
    private String useYn;

    public void changeInfo(Tb301Dto dto) {
        this.itemDiv = dto.getItemDiv();
        this.taxDiv = dto.getTaxDiv();
        this.itemCd = dto.getItemCd();
        this.itemNm = dto.getItemNm();
        this.taxRate = dto.getTaxRate();
        this.useYn = dto.getUseYn();
    }

}
