package doc.mngmnt.entity.document;

import doc.mngmnt.entity.catalog.CatalogEntity;
import doc.mngmnt.entity.file.FileEntity;
import doc.mngmnt.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "document_version")
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class DocumentVersionEntity {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    @NotNull
    private DocumentEntity document;

    @Basic(optional = false)
    @Column(name = "title", nullable = false)
    @NotBlank
    private String title;

    @Basic(optional = false)
    @Column(name = "description", nullable = false)
    @NotBlank
    private String description;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileEntity> files;

    @ManyToMany
    @JoinTable(name = "document_version_document_type",
        joinColumns = {@JoinColumn(name = "document_version_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "document_type_id", referencedColumnName = "id")})
    private Set<DocumentTypeEntity> types;

    @Basic
    @Column(name = "priority")
    @Enumerated(value = EnumType.STRING)
    private DocumentEntity.Importance importance;

    @ManyToOne
    @JoinColumn(name = "catalog_id")
    private CatalogEntity catalog;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "user_document_version",
        joinColumns = {@JoinColumn(name = "document_version_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    @NotEmpty
    private Set<UserEntity> ownerIds;

    public DocumentVersionEntity(DocumentEntity documentEntity) {
        this.document = documentEntity;
        this.title = documentEntity.getTitle();
        this.description = documentEntity.getDescription();
        this.files = documentEntity.getFiles();
        this.types = documentEntity.getTypes();
        this.importance = documentEntity.getImportance();
        this.catalog = documentEntity.getCatalog();
        this.ownerIds = documentEntity.getOwnerIds();
    }
}