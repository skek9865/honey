package project.honey.system.dto;


import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.system.entity.Tb902;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Tb902Dto {

    private Integer seq;

    private String userId;

    private String timeStamp;

    private String ipAddr;

    private Integer remotePort;

    private String sessId;

    private String userAgent;

    public static Tb902 toTb902 (Tb902Dto dto) {
        return Tb902.builder()
                .userId(dto.getUserId())
                .timeStamp(dto.getTimeStamp())
                .ipAddr(dto.getIpAddr())
                .remotePort(dto.getRemotePort())
                .sessId(dto.getSessId())
                .userAgent(dto.getUserAgent())
                .build();
    }

    public static Tb902Dto of(Tb902 entity) {
        return Tb902Dto.builder()
                .seq(entity.getSeq())
                .userId(entity.getUserId())
                .timeStamp(entity.getTimeStamp())
                .ipAddr(entity.getIpAddr())
                .remotePort(entity.getRemotePort())
                .sessId(entity.getSessId())
                .userAgent(entity.getUserAgent())
                .build();
    }
}
