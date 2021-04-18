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

// TODO: 13.04.2021 уточнить по @apiNote
/* 13.04.2021 https://www.youtube.com/watch?v=W1Rtn28lHU8 - про enverse(вроде хорошо рассказали).
    https://github.com/Denuwanhh/spring-audit-demo - пример проекта с гита. */

/**
 * Abstract class for recording persist ops authors.
 *
 * @param <ID> type of {@code id} of auditing entity (type of managing user's id).
 * @apiNote вот этот класс (вроде как) нужен - enverse не смотрит, кто апдейтил.
 * Всякие поля с {@link CreatedDate} и {@link LastModifiedDate} не нужны -
 * эта информация хранится в таблице REVINFO (это можно достать через {@link org.springframework.data.history.Revision}).
 */
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@Getter
@Setter
public abstract class PersistOpsAuthorRecordingEntity<ID> {
    @Basic(optional = false)
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    public ID createdBy;

    @Basic(optional = false)
    @LastModifiedBy
    @Column(name = "updated_by", nullable = false)
    public ID updatedBy;
}

