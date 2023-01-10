package project.honey.business.dto.search;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchPopUp405 {

    private String goodsNm;
    private String type;
    private String exchange; // 대체품목
    private boolean stock;
    private boolean product;
}
