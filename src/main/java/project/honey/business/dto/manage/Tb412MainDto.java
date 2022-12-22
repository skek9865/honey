package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb412;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb412MainDto {

    private Integer seq;
    private String saleNo;
    private String custNm;
    private String empNm;
    private String goods;
    private Integer price;
    private Integer qty;
    private String takeOk;
    private String project;
    private String note;
    private String name;
    private String zipCd;
    private String address;
    private String address1;
    private String note2;
    private String status;
    private String prt;

    public static Tb412MainDto of(Tb412 entity, String custNm, String empNm, String goods, Integer price,
                                  Integer qty, String project, String status){

        String saleNo = entity.getSaleDt().substring(0,4) + "-" + entity.getSaleDt().substring(4,6) + "-" + entity.getSaleDt().substring(6,8) + "-" + entity.getSaleNo();
        String prt = "인쇄전";
        if(entity.getPrtEmp() != null && entity.getPrtDt() != null) prt = "인쇄함";

        return Tb412MainDto.builder()
                .seq(entity.getSeq())
                .saleNo(saleNo)
                .custNm(custNm)
                .empNm(empNm)
                .goods(goods)
                .price(price)
                .qty(qty)
                .takeOk(entity.getTakeOk())
                .project(project)
                .note(entity.getNote())
                .name(entity.getName())
                .zipCd(entity.getZipCd())
                .address(entity.getAddress())
                .address1(entity.getAddress1())
                .note2(entity.getNote2())
                .status(status)
                .prt(prt)
                .build();
    }
}
