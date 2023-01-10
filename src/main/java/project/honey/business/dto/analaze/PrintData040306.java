package project.honey.business.dto.analaze;

import lombok.*;
import project.honey.business.entity.manage.Tb412;
import project.honey.company.entity.Tb101;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
public class PrintData040306 {

    private String corpNo;
    private String corpNm;
    private String ceoNm;
    private String address;
    private String bsns;
    private String item;
    private String fax;
    private String mobile;
    private String stampFnm;
    private String saleDt;
    private String saleNo;
    private String custNm;
    List<PrintData040306_1> tb412_1Dtos;

    public static PrintData040306 of(Tb101 tb101, Tb412 tb412, List<PrintData040306_1> printData040306_1, String custNm){
        String address = tb101.getAddress1() + " " + tb101.getAddress11();
        String saleDt = tb412.getSaleDt().substring(0,4) + " 년" + tb412.getSaleDt().substring(4,6) + " 월" + tb412.getSaleDt().substring(6,8) + " 일";

        return PrintData040306.builder()
                .corpNo(tb101.getCorpno())
                .corpNm(tb101.getCorpnm())
                .ceoNm(tb101.getCeonm())
                .address(address)
                .bsns(tb101.getBsns())
                .item(tb101.getItem())
                .fax(tb101.getCorpfax())
                .mobile(tb101.getMobile())
                .stampFnm(tb101.getStampnm())
                .saleDt(saleDt)
                .saleNo(String.valueOf(tb412.getSaleNo()))
                .custNm(custNm)
                .tb412_1Dtos(printData040306_1)
                .build();
    }
}
