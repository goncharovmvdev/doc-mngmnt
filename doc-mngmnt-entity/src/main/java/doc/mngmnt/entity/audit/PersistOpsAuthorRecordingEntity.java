package doc.mngmnt.entity.audit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

/**
 * Abstract class for recording persist ops authors.
 *
 * @param <ID> type of auditor (usually some type of <i>user</i>).
 */
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@Getter
@Setter
public abstract class PersistOpsAuthorRecordingEntity<ID> {
    @CreatedBy
    private ID createdBy;

    @Basic(optional = false)
    @Column(name = "created", nullable = false, updatable = false)
    @CreatedDate
    private ZonedDateTime created;

    @LastModifiedBy
    private ID updatedBy;

    @Basic(optional = false)
    @Column(name = "updated", nullable = false)
    @LastModifiedDate
    private ZonedDateTime updated;
}

