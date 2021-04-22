package doc.mngmnt.entity.security;

import doc.mngmnt.entity.audit.PersistOpsAuthorRecordingEntity;
import doc.mngmnt.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "\"role\""/* , schema = "public" */)
@Data
@EqualsAndHashCode(callSuper = false, of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity extends PersistOpsAuthorRecordingEntity<Long> {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "name", unique = true, nullable = false)
    @NotBlank
    private String name;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "\"user_role\"",
        joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<UserEntity> users;
}
