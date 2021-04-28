package doc.mngmnt.service.impl.mapper.catalog;

import doc.mngmnt.dto.catalog.AlreadyPresentCatalogDto;
import doc.mngmnt.dto.document.AlreadyPresentDocumentDto;
import doc.mngmnt.entity.catalog.CatalogEntity;
import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.service.api.mapper.Mapper;
import doc.mngmnt.service.impl.mapper.document.DocumentEntityAlreadyPresentDocumentDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class CatalogEntityAlreadyPresentCatalogDtoMapper implements Mapper<CatalogEntity, AlreadyPresentCatalogDto> {
    private final Mapper<DocumentEntity, AlreadyPresentDocumentDto> documentEntityAlreadyPresentDocumentDtoMapper;

    @Autowired
    public CatalogEntityAlreadyPresentCatalogDtoMapper(DocumentEntityAlreadyPresentDocumentDtoMapper documentEntityAlreadyPresentDocumentDtoMapper) {
        this.documentEntityAlreadyPresentDocumentDtoMapper = documentEntityAlreadyPresentDocumentDtoMapper;
    }

    @Override
    public AlreadyPresentCatalogDto map(CatalogEntity from) {
        AlreadyPresentCatalogDto alreadyPresentCatalogDto = new AlreadyPresentCatalogDto();
        alreadyPresentCatalogDto.setId(from.getId());
        alreadyPresentCatalogDto.setName(from.getName());
        alreadyPresentCatalogDto.setDocuments(this.getAlreadyPresentDocumentsFromDocumentEntities(from.getDocuments()));
        alreadyPresentCatalogDto.setParentCatalogId(from.getParentCatalog().getId());
        return alreadyPresentCatalogDto;
    }

    protected Set<AlreadyPresentDocumentDto> getAlreadyPresentDocumentsFromDocumentEntities(Set<DocumentEntity> documentEntities) {
        return documentEntities.stream()
            .map(documentEntityAlreadyPresentDocumentDtoMapper::map)
            .collect(toSet());
    }
}
