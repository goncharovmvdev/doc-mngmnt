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
 * @param <ID> type of {@code id} of auditing entity (type of managing user's id).
 */
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@Getter
@Setter
public abstract class PersistOpsAuthorRecordingEntity<ID> {
    @Basic(optional = false)
    @Column(name = "created_by",updatable = false)
    @CreatedBy
    private ID createdBy;

    @Basic(optional = false)
    @Column(name = "created", nullable = false, updatable = false)
    @CreatedDate
    private ZonedDateTime created;

    @Basic(optional = false)
    @Column(name = "updated_by")
    @LastModifiedBy
    private ID updatedBy;

    @Basic(optional = false)
    @Column(name = "updated", nullable = false)
    @LastModifiedDate
    private ZonedDateTime updated;
}

