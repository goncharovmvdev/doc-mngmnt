package doc.mngmnt.entity.file;

import doc.mngmnt.entity.audit.PersistOpsAuthorRecordingEntity;
import doc.mngmnt.entity.document.DocumentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "\"file\""/* , schema = "public" */)
@Data
@EqualsAndHashCode(callSuper = false, of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity extends PersistOpsAuthorRecordingEntity<Long> {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "path", nullable = false, columnDefinition = "VARCHAR(255) NOT NULL")
    @NotNull
    private String path;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private DocumentEntity documentId;
}
