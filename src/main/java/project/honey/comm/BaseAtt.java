package project.honey.comm;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseAtt {

    @CreatedDate
    @Column(name = "inputdt", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "inputid")
    private String createId;

    @LastModifiedDate
    @Column(name = "updatedt")
    private LocalDateTime modifyDate;

    @Column(name = "updateid")
    private String updateId;
}