package doc.mngmnt.entity.catalog;

import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.entity.document.DocumentVersionEntity;
import doc.mngmnt.entity.user.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "catalog")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"versionDocuments"})
public class CatalogEntity {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "storage_catalog_id", nullable = false, unique = true)
    private String storageCatalogId;

    @Basic(optional = false)
    @Column(name = "original_name", nullable = false)
    private String originalName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private CatalogEntity parentCatalog;

    @OneToMany(mappedBy = "id", cascade = {PERSIST, MERGE})
    private Set<DocumentEntity> documents;

    @OneToMany(mappedBy = "id")
    private Set<DocumentVersionEntity> versionDocuments;

    @ManyToMany(cascade = {MERGE})
    @JoinTable(name = "allowed_user_catalog",
        joinColumns = {@JoinColumn(name = "catalog_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<UserEntity> allowedUsers;
}
