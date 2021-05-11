package doc.mngmnt.repository.security;

import doc.mngmnt.entity.security.RoleEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findAll();

    Set<RoleEntity> findAllByNameLikeIgnoreCase(String nameLike);

    RoleEntity findOneByName(String name);
}
