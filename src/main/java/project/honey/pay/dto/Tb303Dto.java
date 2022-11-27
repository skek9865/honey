package project.honey.pay.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Tb303Dto {

    private Integer seq;

    private String empNo;

    private String itemDiv;

    private String taxDiv;

    private String itemCd;

    private Integer payAmt;

    private String payDt;

    private String rPayDt;
}
