package doc.mngmnt.entity.user;

import doc.mngmnt.entity.audit.PersistOpsAuthorRecordingEntity;
import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.entity.document.DocumentVersionEntity;
import doc.mngmnt.entity.security.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "\"user\""/*, schema = "public" */)
@Data
@EqualsAndHashCode(callSuper = false, of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends PersistOpsAuthorRecordingEntity<Long> {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "password", nullable = false)
    @NotBlank
    private String password;

    @Basic(optional = false)
    @Column(name = "username", nullable = false, unique = true)
    @NotBlank
    private String username;

    @Basic(optional = false)
    @Column(name = "account_non_expired", nullable = false, columnDefinition = "BOOLEAN NOT NULL DEFAULT TRUE")
    @NotNull
    private boolean accountNonExpired;

    @Basic(optional = false)
    @Column(name = "account_non_locked", nullable = false, columnDefinition = "BOOLEAN NOT NULL DEFAULT TRUE")
    @NotNull
    private boolean accountNonLocked;

    @Basic(optional = false)
    @Column(name = "credentials_non_expired", nullable = false, columnDefinition = "BOOLEAN NOT NULL DEFAULT TRUE")
    @NotNull
    private boolean credentialsNonExpired;

    @Basic
    @Column(name = "enabled", nullable = false, columnDefinition = "BOOLEAN NOT NULL DEFAULT TRUE")
    @NotNull
    private boolean enabled;

    @Basic
    @Column(name = "email", columnDefinition = "VARCHAR(255) NOT NULL UNIQUE", unique = true, nullable = false)
    @Email
    private String email;

    @Basic
    @Column(name = "phone_number", columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String phoneNumber;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "\"user_role\"",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<RoleEntity> roles;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "user_document",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "document_id", referencedColumnName = "id")})
    private Set<DocumentEntity> documents;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "user_document_version",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "document_version_id", referencedColumnName = "id")})
    private Set<DocumentVersionEntity> documentsVersions;
}
