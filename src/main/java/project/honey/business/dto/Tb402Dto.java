package project.honey.business.dto;

import lombok.*;
import project.honey.business.entity.Tb402;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb402Dto {

    private Integer seq;
    private String custCd;
    private String custNm;
    private String custGb;
    private String taxGb;
    private String taxCd;
    private String forYn;
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
    private String shipYn;
    private String saleType;
    private String buyType;
    private String note;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public static Tb402Dto of(Tb402 entity){

        return Tb402Dto.builder()
                .seq(entity.getSeq())
                .custCd(entity.getCustCd())
                .custNm(entity.getCustNm())
                .custGb(entity.getCustGb())
                .taxGb(entity.getTaxGb())
                .taxCd(entity.getTaxCd())
                .forYn(entity.getForYn())
                .forNm(entity.getForNm())
                .ceoNm(entity.getCeoNm())
                .bsnS(entity.getBsnS())
                .item(entity.getItem())
                .phone(entity.getPhone())
                .mobile(entity.getMobile())
                .custFax(entity.getCustFax())
                .email(entity.getEmail())
                .zipCd1(entity.getZipCd1())
                .address1(entity.getAddress1())
                .address11(entity.getAddress11())
                .zipCd2(entity.getZipCd2())
                .address2(entity.getAddress2())
                .address21(entity.getAddress21())
                .rAddress(entity.getRAddress())
                .bankBo(entity.getBankBo())
                .sbDay(entity.getSbDay())
                .empCd(entity.getEmpCd())
                .saleCd(entity.getSaleCd())
                .class1(entity.getClass1())
                .class2(entity.getClass2())
                .saleGr(entity.getSaleGr())
                .buyGr(entity.getBuyGr())
                .regDt(entity.getRegDt())
                .homePage(entity.getHomePage())
                .shipYn(entity.getShipYn())
                .buyType(entity.getBuyType())
                .note(entity.getNote())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
