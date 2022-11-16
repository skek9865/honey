package project.honey.comm;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseAtt {

    @CreatedDate
    @Column(name = "inputdt", updatable = false, columnDefinition = "char")
    private String createDate;

    @Column(name = "inputid")
    private String createId;

    @LastModifiedDate
    @Column(name = "updatedt", columnDefinition = "char")
    private String modifyDate;

    @Column(name = "updateid")
    private String updateId;

    @PrePersist
    public void onPrePersist(){
        this.createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        this.modifyDate = this.createDate;
    }

    @PreUpdate
    public void onPreUpdate(){
        this.modifyDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}