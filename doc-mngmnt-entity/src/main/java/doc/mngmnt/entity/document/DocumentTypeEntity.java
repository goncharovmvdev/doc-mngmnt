package doc.mngmnt.entity.document;

import doc.mngmnt.entity.audit.PersistOpsAuthorRecordingEntity;
import doc.mngmnt.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "document_type")
@Data
@EqualsAndHashCode(callSuper = false, of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeEntity extends PersistOpsAuthorRecordingEntity<UserEntity> {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @ManyToMany
    @JoinTable(name = "document_type_document",
        joinColumns = {@JoinColumn(name = "document_type_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "document_id", referencedColumnName = "id")})
    private Set<DocumentEntity> documents;

    @ManyToMany
    @JoinTable(name = "document_version_document_type",
        joinColumns = {@JoinColumn(name = "document_type_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "document_version_id", referencedColumnName = "id")})
    private Set<DocumentVersionEntity> versionedDocuments;
}
