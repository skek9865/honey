package project.honey.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
public class Tb902 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;    // 순번

    @Column(name = "userid")
    private String userId;  // 아이디

    @Column(name = "timestamp")
    private String timeStamp;   // 접속시간

    @Column(name = "ipaddr")
    private String ipAddr;  // 접속IP

    @Column(name = "remoteport")
    private Integer remotePort; // 접속port

    @Column(name = "sessid")
    private String sessId;  //섹션

    @Column(name = "useragent")
    private String userAgent;   //사용자사양

    @Column(name = "inputid")
    private String inputId;     // 입력아이디

    @Column(name = "inputdt", columnDefinition = "char")
    private String inputDt; //입력일시

    @Column(name = "updateid")
    private String updateId;    //수정아이디

    @Column(name = "updatedt", columnDefinition = "char")
    private String updateDt;    //수정일시
}
