package project.honey.pay.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class DtoTotal030105 {

    private Integer totalPayout;
    private Integer totalDeduction;
    private Integer totalActualPayment;

    public static DtoTotal030105 of(List<Dto030105> dtos) {
        int subPayout = 0;
        int subDeduction = 0;
        int subActualPayment = 0;
        for (Dto030105 dto : dtos) {
            subPayout += dto.getPayout();
            subDeduction += dto.getDeduction();
            subActualPayment += dto.getActualPayment();
        }

        return DtoTotal030105.builder()
                .totalPayout(subPayout)
                .totalDeduction(subDeduction)
                .totalActualPayment(subActualPayment)
                .build();
    }
}
