package project.honey.stock.dto.search;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Search060201 {

    private String itemGb;
    private String goodsCd;
    private String goodsNm;
    private List<String> classSeq = new ArrayList<>();

}
