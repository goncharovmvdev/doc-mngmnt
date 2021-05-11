package doc.mngmnt.entity.document;

import doc.mngmnt.entity.catalog.CatalogEntity;
import doc.mngmnt.entity.file.FileEntity;
import doc.mngmnt.entity.user.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "document")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@ToString(of = {"id"})
public class DocumentEntity {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "storage_document_id", unique = true, nullable = false, updatable = false)
    private String storageDocumentId;

    @Basic(optional = false)
    @Column(name = "title", nullable = false)
    private String title;

    @Basic
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "id", cascade = {PERSIST, MERGE})
    private Set<FileEntity> files;

    @ManyToMany
    @JoinTable(name = "document_document_type",
        joinColumns = {@JoinColumn(name = "document_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "document_type_id", referencedColumnName = "id")})
    private Set<DocumentTypeEntity> types;

    @Basic
    @Column(name = "importance")
    @Enumerated(STRING)
    private Importance importance;

    @ManyToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "catalog_id", referencedColumnName = "id")
    private CatalogEntity catalog;

    @ManyToMany
    @JoinTable(name = "moder_user_document",
        joinColumns = {@JoinColumn(name = "document_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<UserEntity> moderIds;

    // TODO: 11.05.2021 ???????????
    @OneToMany(mappedBy = "id")
    private Set<DocumentVersionEntity> versionedDocuments = new HashSet<>();

    @Basic(optional = false)
    @Column(name = "non_deleted", nullable = false, columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    private boolean nonDeleted;

    public void addFile(FileEntity file) {
        this.files.add(file);
        file.setDocumentId(this);
    }

    // TODO: 21.04.2021 ???? может лучше энтити лиснер написать?
    @PrePersist
    protected void prePersistSetVersions() {
        this.setNonDeleted(true);
        saveCurrentVersion();
    }

    @PreUpdate
    protected void preUpdateSetVersions() {
        saveCurrentVersion();
    }

    private void saveCurrentVersion() {
        this.versionedDocuments.add(new DocumentVersionEntity(this));
    }
}
