package doc.mngmnt.service.impl.mapper.catalog;

import doc.mngmnt.dto.catalog.request.SaveCatalogRequest;
import doc.mngmnt.entity.catalog.CatalogEntity;
import doc.mngmnt.repository.catalog.CatalogRepository;
import doc.mngmnt.service.api.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveCatalogRequestCatalogEntityMapper implements Mapper<SaveCatalogRequest, CatalogEntity> {
    private final CatalogRepository catalogRepository;

    @Override
    public CatalogEntity map(SaveCatalogRequest from) {
        CatalogEntity catalogEntity = new CatalogEntity()
        .setOriginalName(from.getOriginalName());
        if (from.getParentCatalogId() == null) {
            catalogEntity.setParentCatalog(null);
            return catalogEntity;
        }
        CatalogEntity parentCatalogEntity = catalogRepository.findById(from.getParentCatalogId())
            .orElse(null);
        catalogEntity.setParentCatalog(parentCatalogEntity);
        return catalogEntity;
    }
}
