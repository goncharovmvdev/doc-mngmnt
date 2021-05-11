package doc.mngmnt.entity.security;

import doc.mngmnt.entity.user.UserEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "\"role\"")
@Data
@Accessors(chain = true)
public class RoleEntity {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "user_role",
        joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<UserEntity> users;
}
