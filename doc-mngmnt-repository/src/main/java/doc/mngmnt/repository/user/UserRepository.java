package doc.mngmnt.repository.user;

import doc.mngmnt.entity.user.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    UserEntity findOneByUsername(String username);

    UserEntity findOneByEmail(String email);

    Set<UserEntity> findAllByEmailOrUsername(String email, String username);

    void deleteByUsername(String username);
}
