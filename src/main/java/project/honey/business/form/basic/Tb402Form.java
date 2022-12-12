package project.honey.business.form.basic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import project.honey.business.entity.basic.Tb402;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Tb402Form {

    private Integer seq;
    private String custCd;
    private String custNm;
    private String custGb;
    private String taxGb;
    private String taxCd;
    private Boolean forYn;
    private String forNm;
    private String ceoNm;
    private String bsnS;
    private String item;
    private String phone;
    private String mobile;
    private String custFax;
    private String email;
    private String zipCd1;
    private String address1;
    private String address11;
    private String zipCd2;
    private String address2;
    private String address21;
    private String rAddress;
    private String bankBo;
    private String sbDay;
    private String empCd;
    private String saleCd;
    private String class1;
    private String class2;
    private String saleGr;
    private String buyGr;
    private String regDt;
    private String homePage;
    private Boolean shipYn;
    private String saleType;
    private String buyType;
    private String note;
    
    public static Tb402 toTb402(Tb402Form form){
        String forYn = "N";
        if(form.getForYn()) forYn = "Y";
        String shipYn = "N";
        if(form.getShipYn()) shipYn = "Y";

        return Tb402.builder()
                .seq(form.getSeq())
                .custCd(form.getCustCd())
                .custNm(form.getCustNm())
                .custGb(form.getCustGb())
                .taxGb(form.getTaxGb())
                .taxCd(form.getTaxCd())
                .forYn(forYn)
                .forNm(form.getForNm())
                .ceoNm(form.getCeoNm())
                .bsnS(form.getBsnS())
                .item(form.getItem())
                .phone(form.getPhone())
                .mobile(form.getMobile())
                .custFax(form.getCustFax())
                .email(form.getEmail())
                .zipCd1(form.getZipCd1())
                .address1(form.getAddress1())
                .address11(form.getAddress11())
                .zipCd2(form.getZipCd2())
                .address2(form.getAddress2())
                .address21(form.getAddress21())
                .rAddress(form.getRAddress())
                .bankBo(form.getBankBo())
                .sbDay(form.getSbDay())
                .empCd(form.getEmpCd())
                .saleCd(form.getSaleCd())
                .class1(form.getClass1())
                .class2(form.getClass2())
                .saleGr(form.getSaleGr())
                .buyGr(form.getBuyGr())
                .regDt(form.getRegDt())
                .homePage(form.getHomePage())
                .shipYn(shipYn)
                .saleType(form.getSaleType())
                .buyType(form.getBuyType())
                .note(form.getNote())
                .build();
    }
}
