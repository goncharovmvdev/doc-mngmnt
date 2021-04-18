package doc.mngmnt.entity.document;

import doc.mngmnt.entity.audit.PersistOpsAuthorRecordingEntity;
import doc.mngmnt.entity.catalog.CatalogEntity;
import doc.mngmnt.entity.file.FileEntity;
import doc.mngmnt.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.history.RevisionMetadata;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/* TODO: 18.04.2021 (организационный вопрос) как мы понимаем, что у документа надо обновить версию?
    Что не надо аудировать? (@NotAudited) */
@Entity
@Table(name = "\"document\"")
@Audited
@Data
@EqualsAndHashCode(callSuper = false, of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class DocumentEntity extends PersistOpsAuthorRecordingEntity<Long> {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "title", nullable = false)
    @NotNull
    private String title;

    @Basic(optional = false)
    @Column(name = "description", nullable = false)
    @NotNull
    private String description;

    @Basic
    @Column(name = "priority")
    @Enumerated(value = EnumType.STRING)
    private Importance priority;

    public static enum Importance {
        LOW, MEDIUM, HIGH
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id", referencedColumnName = "id")
    private CatalogEntity catalog;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "\"document_type_document\"",
        joinColumns = {@JoinColumn(name = "document_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "document_type_id", referencedColumnName = "id")})
    private Set<DocumentTypeEntity> types;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "\"user_document\"",
        joinColumns = {@JoinColumn(name = "document_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<UserEntity> ownerIds;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileEntity> files;

    @Transient
    private RevisionMetadata<Long> version;
}
