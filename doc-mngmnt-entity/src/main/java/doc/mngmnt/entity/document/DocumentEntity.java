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
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

// TODO: 21.04.2021 что считать за изменение в документе.
@Entity
@Table(name = "document")
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class DocumentEntity {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "title", nullable = false)
    @NotBlank
    private String title;

    @Basic(optional = false)
    @Column(name = "description", nullable = false)
    @NotBlank
    private String description;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private Set<FileEntity> files;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "document_document_type",
        joinColumns = {@JoinColumn(name = "document_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "document_type_id", referencedColumnName = "id")})
    private Set<DocumentTypeEntity> types;

    @Basic
    @Column(name = "importance")
    @Enumerated(value = EnumType.STRING)
    private Importance importance;

    public static enum Importance {
        LOW, MEDIUM, HIGH
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id", referencedColumnName = "id")
    private CatalogEntity catalog;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "user_document",
        joinColumns = {@JoinColumn(name = "document_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<UserEntity> ownerIds;

    @OneToMany(mappedBy = "id", orphanRemoval = true, cascade = {CascadeType.ALL})
    private Set<DocumentVersionEntity> versions;

    // TODO: 21.04.2021 ???? может лучше энтити лиснер написать?
    @PrePersist
    protected void prePersistSetVersions() {
        saveCurrentVersion();
    }

    @PreUpdate
    protected void preUpdateSetVersions() {
        saveCurrentVersion();
    }

    protected void saveCurrentVersion() {
        this.versions.add(new DocumentVersionEntity(this));
    }
}
