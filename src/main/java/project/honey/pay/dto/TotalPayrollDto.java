package project.honey.pay.dto;

import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class TotalPayrollDto {

    private List<Integer> totalPays = new ArrayList<>();
    private Integer totalPayout;
    private Integer totalDeduction;
    private Integer totalActualPayment;
    private int count;

    public static TotalPayrollDto of(List<PayrollDto> dtos, List<String> useTitles) {
        int subPayout = 0;
        int subDeduction = 0;
        int subActualPayment = 0;

        // result 값이 없을때 대체할 리스트
        List<Integer> zeroList = useTitles.stream().map(m -> 0).collect(Collectors.toList());

        Map<Integer, Integer> map = new HashMap<>();

        for (PayrollDto dto : dtos) {

            List<Integer> pays = dto.getPays();

            for (int i = 0; i < pays.size(); i++) {
                if (map.containsKey(i)) {
                    map.put(i, map.get(i) + pays.get(i));
                }else{
                    map.put(i, pays.get(i));
                }
            }
            subPayout += dto.getPayout();
            subDeduction += dto.getDeduction();
            subActualPayment += dto.getActualPayment();
        }

        List<Integer> result = new ArrayList<>(map.values());

        return TotalPayrollDto.builder()
                .totalPays(result.size()!=0?result:zeroList)
                .totalPayout(subPayout)
                .totalDeduction(subDeduction)
                .totalActualPayment(subActualPayment)
                .count(zeroList.size() + 7)
                .build();
    }
}
