package project.honey.system.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CodeDto {

    private String text;
    private String value;

    @QueryProjection
    public CodeDto(String text, String value) {
        this.text = text;
        this.value = value;
    }
}
