package project.honey.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.system.dto.Tb902Dto;

import javax.persistence.*;

/*
 * 사용 이력
 * HistoryOfUse
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_902")
public class Tb902  extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @Comment("아이디")
    @Column(name = "userid")
    private String userId;

    @Comment("접속시간")
    @Column(name = "timestamp")
    private String timeStamp;

    @Comment("접속IP")
    @Column(name = "ipaddr")
    private String ipAddr;

    @Comment("접속port")
    @Column(name = "remoteport")
    private Integer remotePort;

    @Comment("섹션")
    @Column(name = "sessid")
    private String sessId;

    @Comment("사용자사양")
    @Column(name = "useragent")
    private String userAgent;

    public void changeInfo(Tb902Dto dto) {
        this.userId = dto.getUserId();
        this.timeStamp = dto.getTimeStamp();
        this.ipAddr = dto.getIpAddr();
        this.remotePort = dto.getRemotePort();
        this.sessId = dto.getSessId();
        this.userAgent = dto.getUserAgent();
    }
}
