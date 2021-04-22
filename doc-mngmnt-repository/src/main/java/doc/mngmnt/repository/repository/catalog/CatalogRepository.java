package doc.mngmnt.repository.repository.catalog;

import doc.mngmnt.entity.catalog.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CatalogRepository extends JpaRepository<CatalogEntity, Long> {

    CatalogEntity findOneByName(String name);

    Set<CatalogEntity> findAllByNameLikeIgnoreCase(String nameLike);
}
