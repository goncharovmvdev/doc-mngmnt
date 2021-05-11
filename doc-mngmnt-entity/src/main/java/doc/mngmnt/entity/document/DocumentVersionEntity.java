package doc.mngmnt.entity.document;

import doc.mngmnt.entity.catalog.CatalogEntity;
import doc.mngmnt.entity.file.FileEntity;
import doc.mngmnt.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.EnumType.STRING;

// TODO: 01.05.2021 в  DocEntity хранить версию, pk -> id + document_id (здесь)
@Entity
@Table(name = "document_version")
@Data
@Accessors(chain = true)
public class DocumentVersionEntity {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private DocumentEntity document;

    @Basic(optional = false)
    @Column(name = "title", nullable = false)
    private String title;

    @Basic(optional = false)
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(cascade = {ALL})
    @JoinTable(name = "document_version_file",
        joinColumns = {@JoinColumn(name = "document_version_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "file_id", referencedColumnName = "id")})
    private Set<FileEntity> files;

    @ManyToMany
    @JoinTable(name = "document_version_document_type",
        joinColumns = {@JoinColumn(name = "document_version_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "document_type_id", referencedColumnName = "id")})
    private Set<DocumentTypeEntity> types;

    @Basic
    @Column(name = "importance")
    @Enumerated(STRING)
    private Importance importance;

    @ManyToOne(cascade = {MERGE})
    @JoinColumn(name = "catalog_id")
    private CatalogEntity catalog;

    @ManyToMany
    @JoinTable(name = "user_document_version",
        joinColumns = {@JoinColumn(name = "document_version_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<UserEntity> moderIds;

    DocumentVersionEntity(DocumentEntity documentEntity) {
        this.document = documentEntity;
        this.title = documentEntity.getTitle();
        this.description = documentEntity.getDescription();
        this.files = documentEntity.getFiles();
        this.types = documentEntity.getTypes();
        this.importance = documentEntity.getImportance();
        this.catalog = documentEntity.getCatalog();
        this.moderIds = documentEntity.getModerIds();
    }
}