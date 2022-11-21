package project.honey.comm.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class InputMenuIdDto {
    private String iFstId;
    private String iScdId;
    private String iThdId;
    private String iMenuNm;
}
