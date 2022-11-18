package project.honey.company.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.honey.comm.BaseAtt;
import project.honey.company.CompanyForm;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Getter
@Table(name = "tb_101")
public class Tb101 extends BaseAtt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Column(name = "corpnm")
    private String corpnm;

    @Column(name = "corpno")
    private String corpno;

    @Column(name = "ceonm")
    private String ceonm;

    @Column(name = "setdt", columnDefinition = "char")
    private String setdt;

    @Column(name = "corptel")
    private String corptel;

    @Column(name = "hometel")
    private String hometel;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "corpfax")
    private String corpfax;

    @Column(name = "hompage")
    private String hompage;

    @Column(name = "zipcd1")
    private String zipcd1;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address11")
    private String address11;

    @Column(name = "zipcd2")
    private String zipcd2;

    @Column(name = "address2")
    private String address2;

    @Column(name = "address21")
    private String address21;

    @Column(name = "corpeng")
    private String corpeng;

    @Column(name = "zipcdeng")
    private String zipcdeng;

    @Column(name = "addresseng")
    private String addresseng;

    @Column(name = "addresseng1")
    private String addresseng1;

    @Column(name = "corpregno")
    private String corpregno;

    @Column(name = "bsns")
    private String bsns;

    @Column(name = "item")
    private String item;

    @Column(name = "logonm")
    private String logonm;

    @Column(name = "stampnm")
    private String stampnm;

    public void changeInfo(CompanyForm form){
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
        if(!form.getLogonm().isEmpty()) this.logonm = form.getLogonm().getOriginalFilename();
        if(!form.getStampnm().isEmpty()) this.stampnm = form.getStampnm().getOriginalFilename();
    }
}