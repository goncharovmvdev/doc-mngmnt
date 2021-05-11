package doc.mngmnt.entity.document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "document_type")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"versionedDocuments"})
public class DocumentTypeEntity {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(name = "document_document_type",
        joinColumns = {@JoinColumn(name = "document_type_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "document_id", referencedColumnName = "id")})
    private Set<DocumentEntity> documents;

    @ManyToMany
    @JoinTable(name = "document_version_document_type",
        joinColumns = {@JoinColumn(name = "document_type_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "document_version_id", referencedColumnName = "id")})
    private Set<DocumentVersionEntity> versionedDocuments;
}
