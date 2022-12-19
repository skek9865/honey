package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb410;
import project.honey.company.entity.Tb101;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
public class PrintData040201 {

    private String corpNo;
    private String corpNm;
    private String ceoNm;
    private String address;
    private String bsns;
    private String item;
    private String corpTel;
    private String fax;
    private String stampFnm;
    private String estDt;
    private String custNm;
    private String empNm;
    private String note;
    private String expDt;
    private String payCondit;
    List<PrintData040201_1> tb410_1Dtos;

    public static PrintData040201 of(Tb101 tb101, Tb410 tb410, List<PrintData040201_1> tb410_1Dtos, String custNm, String empNm){
        String address = tb101.getAddress1() + " " + tb101.getAddress11();
        String estDt = tb410.getEstimDt().substring(0,4) + ". " + tb410.getEstimDt().substring(4,6) + ". " + tb410.getEstimDt().substring(6,8);

        return PrintData040201.builder()
                .corpNo(tb101.getCorpno())
                .corpNm(tb101.getCorpnm())
                .ceoNm(tb101.getCeonm())
                .address(address)
                .bsns(tb101.getBsns())
                .item(tb101.getItem())
                .corpTel(tb101.getCorptel())
                .fax(tb101.getCorpfax())
                .stampFnm(tb101.getStampnm())
                .estDt(estDt)
                .custNm(custNm)
                .empNm(empNm)
                .note(tb410.getNote())
                .expDt(tb410.getExpDt())
                .payCondit(tb410.getPayCondit())
                .tb410_1Dtos(tb410_1Dtos)
                .build();
    }
}
