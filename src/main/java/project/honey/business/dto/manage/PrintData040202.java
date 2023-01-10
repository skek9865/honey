package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb411;
import project.honey.company.entity.Tb101;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
public class PrintData040202 {

    private String corpNo;
    private String corpNm;
    private String ceoNm;
    private String address;
    private String bsns;
    private String item;
    private String fax;
    private String mobile;
    private String stampFnm;
    private String orderDt;
    private String orderNo;
    private String custNm;
    List<PrintData040202_1> tb411_1Dtos;

    public static PrintData040202 of(Tb101 tb101, Tb411 tb411, List<PrintData040202_1> printData040202_1s, String custNm){
        String address = tb101.getAddress1() + " " + tb101.getAddress11();
        String orderDt = tb411.getOrderDt().substring(0,4) + " 년" + tb411.getOrderDt().substring(4,6) + " 월" + tb411.getOrderDt().substring(6,8) + " 일";

        return PrintData040202.builder()
                .corpNo(tb101.getCorpno())
                .corpNm(tb101.getCorpnm())
                .ceoNm(tb101.getCeonm())
                .address(address)
                .bsns(tb101.getBsns())
                .item(tb101.getItem())
                .fax(tb101.getCorpfax())
                .mobile(tb101.getMobile())
                .stampFnm(tb101.getStampnm())
                .orderDt(orderDt)
                .orderNo(String.valueOf(tb411.getOrderNo()))
                .custNm(custNm)
                .tb411_1Dtos(printData040202_1s)
                .build();
    }
}
