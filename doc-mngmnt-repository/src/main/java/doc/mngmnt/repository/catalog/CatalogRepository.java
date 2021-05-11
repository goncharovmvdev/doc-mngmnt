package doc.mngmnt.repository.catalog;

import doc.mngmnt.entity.catalog.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Set;

public interface CatalogRepository extends JpaRepository<CatalogEntity, Long>, JpaSpecificationExecutor {

    Set<CatalogEntity> findAllByOriginalNameLikeIgnoreCase(String nameLike);

    Set<CatalogEntity> findAllByParentCatalog(CatalogEntity parentCatalog);
}
