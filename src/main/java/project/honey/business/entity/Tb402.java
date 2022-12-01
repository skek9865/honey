package project.honey.business.entity;

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
@ToString
@Table(name = "tb_402")
public class Tb402 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("거래처코드")
    @Column(name = "custcd", length = 10, columnDefinition = "char")
    private String custCd;

    @NotNull
    @Comment("상호(이름)")
    @Column(name = "custnm", length = 50)
    private String custNm;

    @NotNull
    @Comment("거래처코드구분")
    @Column(name = "custgb", length = 5, columnDefinition = "char")
    private String custGb;

    @NotNull
    @Comment("세무신고구분")
    @Column(name = "taxgb", length = 5, columnDefinition = "char")
    private String taxGb;

    @Comment("세무신고코드")
    @Column(name = "taxcd", length = 50)
    private String taxCd;

    @Comment("외화거래처")
    @Column(name = "foryn", length = 2, columnDefinition = "char")
    private String forYn;

    @NotNull
    @Comment("외화명")
    @Column(name = "fornm", length = 50)
    private String forNm;

    @Comment("대표자명")
    @Column(name = "ceonm", length = 50)
    private String ceoNm;

    @Comment("업태")
    @Column(name = "bsns", length = 50)
    private String bsnS;

    @Comment("종목")
    @Column(length = 50)
    private String item;

    @Comment("전화번호")
    @Column(length = 20)
    private String phone;

    @Comment("모바일")
    @Column(length = 20)
    private String mobile;

    @Comment("거래처Fax")
    @Column(name = "custfax", length = 20)
    private String custFax;

    @Comment("Email")
    @Column(length = 50)
    private String email;

    @Comment("우편번호1")
    @Column(name = "zipcd1", length = 10)
    private String zipCd1;

    @Comment("주소1")
    @Column(length = 50)
    private String address1;

    @Comment("주소상세1")
    @Column(length = 50)
    private String address11;

    @Comment("우편번호2")
    @Column(name = "zipcd2", length = 10)
    private String zipCd2;

    @Comment("주소2")
    @Column(length = 50)
    private String address2;

    @Comment("주소상세2")
    @Column(length = 50)
    private String address21;

    @Comment("실제주소")
    @Column(name = "raddress", length = 50)
    private String rAddress;

    @Comment("통장번호")
    @Column(name = "bankbo", length = 50)
    private String bankBo;

    @Comment("수금/지급예정일")
    @Column(name = "sbday", length = 50)
    private String sbDay;

    @Comment("담당자")
    @Column(name = "empcd", length = 50)
    private String empCd;

    @Comment("영업사원")
    @Column(name = "salecd", length = 10)
    private String saleCd;

    @Comment("거래처그룹1")
    @Column(length = 5, columnDefinition = "char")
    private String class1;

    @Comment("거래처그룹2")
    @Column(length = 5, columnDefinition = "char")
    private String class2;

    @Comment("영업단가그룹")
    @Column(name = "salegr", length = 5, columnDefinition = "char")
    private String saleGr;

    @Comment("구매단가그룹")
    @Column(name = "buygr", length = 5, columnDefinition = "char")
    private String buyGr;

    @Comment("거래 개설일")
    @Column(name = "regdt", length = 50)
    private String regDt;

    @Comment("홈페이지")
    @Column(name = "homepage", length = 50)
    private String homePage;

    @Comment("출하대상거래처")
    @Column(name = "shipyn", length = 5, columnDefinition = "char")
    private String shipYn;

    @Comment("거래유형(영업)")
    @Column(name = "saletype", length = 5, columnDefinition = "char")
    private String saleType;

    @Comment("거래유형(구매)")
    @Column(name = "buytype", length = 5, columnDefinition = "char")
    private String buyType;

    @Comment("비고")
    @Column(length = 255)
    private String note;
}
