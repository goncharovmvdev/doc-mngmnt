package doc.mngmnt.entity.document;

import doc.mngmnt.entity.audit.PersistOpsAuthorRecordingEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "document_type")
@Data
@EqualsAndHashCode(callSuper = false, of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeEntity extends PersistOpsAuthorRecordingEntity<Long> {
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    @NotNull
    private String name;

    @ManyToMany
    @JoinTable(name = "document_type_document",
        joinColumns = {@JoinColumn(name = "document_type_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "document_id", referencedColumnName = "id")})
    private Set<DocumentEntity> documents;

    @ManyToMany
    @JoinTable(name = "document_type_document_version",
        joinColumns = {@JoinColumn(name = "document_type_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "document_version_id", referencedColumnName = "id")})
    private Set<DocumentVersionEntity> versionedDocuments;
}
