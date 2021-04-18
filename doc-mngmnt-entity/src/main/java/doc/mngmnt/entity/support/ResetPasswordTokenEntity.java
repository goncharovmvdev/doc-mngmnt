package doc.mngmnt.entity.support;

import doc.mngmnt.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Entity class representing reset passwords tokens.
 */
@Entity
@Table(name = "reset_password_token"/* , schema = "public" */)
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordTokenEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "token", columnDefinition = "VARCHAR(255) NOT NULL", nullable = false, updatable = false)
    @NotBlank
    private String token;

    @Basic(optional = false)
    @Column(name = "expiry", nullable = false, updatable = false)
    @NotNull
    private ZonedDateTime expiry;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", unique = true, nullable = false, updatable = false)
    @NotNull
    private UserEntity userId;
}
