package doc.mngmnt.repository.support;

import doc.mngmnt.entity.support.ResetPasswordTokenEntity;
import doc.mngmnt.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordTokenEntity, Long> {

    ResetPasswordTokenEntity findOneByToken(String token);

    ResetPasswordTokenEntity findOneByUserId(UserEntity usersId);
}
