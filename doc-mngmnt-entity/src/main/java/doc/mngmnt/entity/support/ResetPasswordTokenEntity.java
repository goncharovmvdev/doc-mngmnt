package doc.mngmnt.entity.support;

import doc.mngmnt.entity.user.UserEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.ZonedDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "reset_password_token")
@Data
@Accessors(chain = true)
public class ResetPasswordTokenEntity {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "token", columnDefinition = "VARCHAR(255) NOT NULL", nullable = false, updatable = false)
    private String token;

    @Basic(optional = false)
    @Column(name = "expiry", nullable = false, updatable = false)
    private ZonedDateTime expiry;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", unique = true, nullable = false, updatable = false)
    private UserEntity userId;
}
