package project.honey.business.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.business.form.Tb402Form;
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

    public void updateData(Tb402Form form){
        String forYn = "N";
        if(form.getForYn()) forYn = "Y";
        String shipYn = "N";
        if(form.getShipYn()) shipYn = "Y";

        this.seq = form.getSeq();
        this.custCd = form.getCustCd();
        this.custNm = form.getCustNm();
        this.custGb = form.getCustGb();
        this.taxGb = form.getTaxGb();
        this.taxCd = form.getTaxCd();
        this.forYn = forYn;
        this.forNm = form.getForNm();
        this.ceoNm = form.getCeoNm();
        this.bsnS = form.getBsnS();
        this.item = form.getItem();
        this.phone = form.getPhone();
        this.mobile = form.getMobile();
        this.custFax = form.getCustFax();
        this.email = form.getEmail();
        this.zipCd1 = form.getZipCd1();
        this.address1 = form.getAddress1();
        this.address11 = form.getAddress11();
        this.zipCd2 = form.getZipCd2();
        this.address2 = form.getAddress2();
        this.address21 = form.getAddress21();
        this.rAddress = form.getRAddress();
        this.bankBo = form.getBankBo();
        this.sbDay = form.getSbDay();
        this.empCd = form.getEmpCd();
        this.saleCd = form.getSaleCd();
        this.class1 = form.getClass1();
        this.class2 = form.getClass2();
        this.saleGr = form.getSaleGr();
        this.buyGr = form.getBuyGr();
        this.regDt = form.getRegDt();
        this.homePage = form.getHomePage();
        this.shipYn = shipYn;
        this.saleType = form.getSaleType();
        this.buyType = form.getBuyType();
        this.note = form.getNote();
    }
}
