package doc.mngmnt.service.api.catalog;

import doc.mngmnt.dto.catalog.AlreadyPresentCatalogDto;
import doc.mngmnt.entity.catalog.CatalogEntity;

import java.util.Set;

public interface CatalogService {

    CatalogEntity findById(Long catalogId);

    CatalogEntity findOneByName(String name);

    Set<AlreadyPresentCatalogDto> findChildCatalogsByParentId(Long parentId);
}
