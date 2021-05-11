package doc.mngmnt.service.impl.mapper.catalog;

import doc.mngmnt.dto.catalog.response.AlreadyPresentCatalogResponse;
import doc.mngmnt.dto.document.response.AlreadyPresentDocumentResponse;
import doc.mngmnt.entity.catalog.CatalogEntity;
import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.service.api.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class CatalogEntityAlreadyPresentCatalogDtoMapper implements Mapper<CatalogEntity, AlreadyPresentCatalogResponse> {
    private final Mapper<DocumentEntity, AlreadyPresentDocumentResponse> documentEntityAlreadyPresentDocumentDtoMapper;

    @Override
    public AlreadyPresentCatalogResponse map(CatalogEntity from) {
        AlreadyPresentCatalogResponse alreadyPresentCatalogResponse = new AlreadyPresentCatalogResponse()
            .setId(from.getId())
            .setName(from.getOriginalName());
        if (from.getParentCatalog() != null) {
            alreadyPresentCatalogResponse.setParentCatalogId(from.getParentCatalog().getId());
        }
        return alreadyPresentCatalogResponse.setDocuments(this.getAlreadyPresentDocumentsFromDocumentEntities(from.getDocuments()));
    }

    private Set<AlreadyPresentDocumentResponse> getAlreadyPresentDocumentsFromDocumentEntities(Set<DocumentEntity> documentEntities) {
        if (documentEntities != null) {
            return documentEntities.stream()
                .map(documentEntityAlreadyPresentDocumentDtoMapper::map)
                .collect(toSet());
        }
        return new HashSet<>();
    }
}
