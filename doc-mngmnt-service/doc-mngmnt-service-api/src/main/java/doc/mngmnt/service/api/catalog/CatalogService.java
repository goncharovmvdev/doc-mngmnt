package doc.mngmnt.service.api.catalog;

import doc.mngmnt.dto.catalog.request.UpdateCatalogRequest;
import doc.mngmnt.dto.catalog.response.AlreadyPresentCatalogResponse;
import doc.mngmnt.dto.catalog.request.SaveCatalogRequest;
import doc.mngmnt.dto.document.request.UpdateDocumentRequest;
import doc.mngmnt.dto.document.response.AlreadyPresentDocumentResponse;

import java.util.Set;

public interface CatalogService {

    AlreadyPresentCatalogResponse save(SaveCatalogRequest saveCatalogRequest);

    AlreadyPresentCatalogResponse findById(Long catalogId);

    Set<AlreadyPresentCatalogResponse> findAllByOriginalNameLikeIgnoreCase(String nameLike);

    Set<AlreadyPresentCatalogResponse> findAllChildrenByParentCatalogId(Long parentId);

    AlreadyPresentCatalogResponse updateById(UpdateCatalogRequest updateCatalogRequest);
}
