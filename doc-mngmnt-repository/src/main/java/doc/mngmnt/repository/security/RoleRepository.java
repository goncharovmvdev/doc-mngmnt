package doc.mngmnt.repository.security;

import doc.mngmnt.entity.security.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findAll();

    RoleEntity findOneByName(String name);
}
