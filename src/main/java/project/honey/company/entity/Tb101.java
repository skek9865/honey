package project.honey.company.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.company.dto.CompanyForm;

import javax.persistence.*;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Getter
@Table(name = "tb_101")
public class Tb101 extends BaseAtt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @Column(name = "corpnm")
    @Comment("회사명")
    private String corpnm;

    @Column(name = "corpno")
    @Comment("사업자등록번호")
    private String corpno;

    @Column(name = "ceonm")
    @Comment("대표자명")
    private String ceonm;

    @Column(name = "setdt", columnDefinition = "char")
    @Comment("설립일자")
    private String setdt;

    @Column(name = "corptel")
    @Comment("회사전화")
    private String corptel;

    @Column(name = "hometel")
    @Comment("자택전화")
    private String hometel;

    @Column(name = "email")
    @Comment("이메일")
    private String email;

    @Column(name = "mobile")
    @Comment("모바일")
    private String mobile;

    @Column(name = "corpfax")
    @Comment("회사Fax")
    private String corpfax;

    @Column(name = "hompage")
    @Comment("홈페이지")
    private String hompage;

    @Column(name = "zipcd1")
    @Comment("우편번호1")
    private String zipcd1;

    @Column(name = "address1")
    @Comment("주소1")
    private String address1;

    @Column(name = "address11")
    @Comment("주소상세1")
    private String address11;

    @Column(name = "zipcd2")
    @Comment("우편번호2")
    private String zipcd2;

    @Column(name = "address2")
    @Comment("주소2")
    private String address2;

    @Column(name = "address21")
    @Comment("주소상세2")
    private String address21;

    @Column(name = "corpeng")
    @Comment("회사영문명")
    private String corpeng;

    @Column(name = "zipcdeng")
    @Comment("영문우편번호")
    private String zipcdeng;

    @Column(name = "addresseng")
    @Comment("영문주소")
    private String addresseng;

    @Column(name = "addresseng1")
    @Comment("영문주소상세")
    private String addresseng1;

    @Column(name = "corpregno")
    @Comment("법인등록번호")
    private String corpregno;

    @Column(name = "bsns")
    @Comment("업태")
    private String bsns;

    @Column(name = "item")
    @Comment("종목")
    private String item;

    @Column(name = "logonm")
    @Comment("회사로고파일명")
    private String logonm;

    @Column(name = "stampnm")
    @Comment("도장이미지파일명")
    private String stampnm;

    public void changeInfo(CompanyForm form, Map<String, String> imagenm){
        this.corpnm = form.getCorpnm();
        this.corpno = form.getCorpno();
        this.ceonm = form.getCorpnm();
        this.setdt = form.getSetdt();
        this.corptel = form.getCorptel();
        this.hometel = form.getHometel();
        this.email = form.getEmail();
        this.mobile = form.getMobile();
        this.corpfax = form.getCorpfax();
        this.hompage = form.getHompage();
        this.zipcd1 = form.getZipcd1();
        this.address1 = form.getAddress1();
        this.address11 = form.getAddress11();
        this.zipcd2 = form.getZipcd2();
        this.address2 = form.getAddress2();
        this.address21 = form.getAddress21();
        this.corpeng = form.getCorpeng();
        this.zipcdeng = form.getZipcdeng();
        this.addresseng = form.getAddresseng();
        this.addresseng1 = form.getAddresseng1();
        this.corpregno = form.getCorpregno();
        this.bsns = form.getBsns();
        this.item = form.getItem();
        if(imagenm.get("logonm") != null) this.logonm = imagenm.get("logonm");
        if(imagenm.get("stampnm") != null) this.stampnm = imagenm.get("stampnm");
    }
}
