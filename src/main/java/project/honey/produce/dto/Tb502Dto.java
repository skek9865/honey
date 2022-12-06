package project.honey.produce.dto;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.produce.entity.Tb501;
import project.honey.produce.entity.Tb502;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb502Dto {

    private Integer seq;

    private String faulyCd;

    private String faulyNm;

    private String note;

    private Integer errorAl;

    private String useYn;

    public void setUseYn(String useYn) {
        this.useYn = useYn!=null?useYn:"N";
    }

    public static Tb502 toTb502(Tb502Dto dto) {
        return Tb502.builder()
                .faulyCd(dto.getFaulyCd())
                .faulyNm(dto.getFaulyNm())
                .note(dto.getNote())
                .errorAl(dto.getErrorAl())
                .useYn(dto.getUseYn())
                .build();
    }

    public static Tb502Dto of(Tb502 entity) {
        return Tb502Dto.builder()
                .seq(entity.getSeq())
                .faulyCd(entity.getFaulyCd())
                .faulyNm(entity.getFaulyNm())
                .note(entity.getNote())
                .errorAl(entity.getErrorAl())
                .useYn(entity.getUseYn())
                .build();
    }
}
