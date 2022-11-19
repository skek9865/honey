package project.honey.system.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CodeDto {

    private String scdId;
    private String codeNm;

    @QueryProjection
    public CodeDto(String scdId, String codeNm) {
        this.scdId = scdId;
        this.codeNm = codeNm;
    }
}
