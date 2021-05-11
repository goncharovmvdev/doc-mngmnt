package doc.mngmnt.entity.user;

import doc.mngmnt.entity.catalog.CatalogEntity;
import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.entity.document.DocumentVersionEntity;
import doc.mngmnt.entity.security.RoleEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "\"user\"")
@Data
@EqualsAndHashCode(of = "id")
@ToString(of = {"id"})
@Accessors(chain = true)
public class UserEntity {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "password", nullable = false)
    private String password;

    @Basic(optional = false)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Basic(optional = false)
    @Column(name = "account_non_expired", nullable = false, columnDefinition = "BOOLEAN NOT NULL DEFAULT TRUE")
    private boolean accountNonExpired;

    @Basic(optional = false)
    @Column(name = "account_non_locked", nullable = false, columnDefinition = "BOOLEAN NOT NULL DEFAULT TRUE")
    private boolean accountNonLocked;

    @Basic(optional = false)
    @Column(name = "credentials_non_expired", nullable = false, columnDefinition = "BOOLEAN NOT NULL DEFAULT TRUE")
    private boolean credentialsNonExpired;

    @Basic
    @Column(name = "enabled", nullable = false, columnDefinition = "BOOLEAN NOT NULL DEFAULT TRUE")
    private boolean enabled;

    @Basic
    @Column(name = "email", columnDefinition = "VARCHAR(255) NOT NULL UNIQUE", unique = true, nullable = false)
    private String email;

    @Basic
    @Column(name = "phone_number", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String phoneNumber;

    @ManyToMany
    @JoinTable(name = "user_role",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<RoleEntity> roles;

    @ManyToMany(cascade = {MERGE})
    @JoinTable(name = "allowed_user_catalog",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "catalog_id", referencedColumnName = "id")})
    private Set<CatalogEntity> allowedCatalogs;

    @ManyToMany(cascade = {MERGE})
    @JoinTable(name = "moder_user_document",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "document_id", referencedColumnName = "id")})
    private Set<DocumentEntity> documents;

    @ManyToMany
    @JoinTable(name = "user_document_version",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "document_version_id", referencedColumnName = "id")})
    private Set<DocumentVersionEntity> documentsVersions;
}
