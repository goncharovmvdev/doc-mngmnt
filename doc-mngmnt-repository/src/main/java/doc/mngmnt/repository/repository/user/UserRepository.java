package doc.mngmnt.repository.repository.user;

import doc.mngmnt.entity.user.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @EntityGraph(attributePaths = {"roles"})
    UserEntity findOneByUsername(String username);

    UserEntity findOneByEmail(String email);

    UserEntity findOneByEmailOrUsername(String email, String username);

    void deleteByUsername(String username);
}
