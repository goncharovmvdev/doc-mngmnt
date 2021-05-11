package doc.mngmnt.entity.file;

import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.entity.document.DocumentVersionEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "file")
@Data
@Accessors(chain = true)
public class FileEntity {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "storage_file_id", unique = true, nullable = false)
    private String storageFileId;

    @Basic(optional = false)
    @Column(name = "original_name", nullable = false)
    private String originalName;

    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private DocumentEntity documentId;

    @ManyToMany
    @JoinTable(name = "document_version_file",
        joinColumns = {@JoinColumn(name = "file_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "document_version_id", referencedColumnName = "id")})
    private Set<DocumentVersionEntity> documentVersionEntities;
}
