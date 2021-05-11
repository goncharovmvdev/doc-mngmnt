package doc.mngmnt.service.impl.mapper.document;

import doc.mngmnt.dto.document.response.AlreadyPresentDocumentResponse;
import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.entity.document.DocumentTypeEntity;
import doc.mngmnt.entity.file.FileEntity;
import doc.mngmnt.entity.user.UserEntity;
import doc.mngmnt.service.api.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class DocumentEntityAlreadyPresentDocumentResponseMapper implements Mapper<DocumentEntity, AlreadyPresentDocumentResponse> {

    @Override
    public AlreadyPresentDocumentResponse map(DocumentEntity from) {
        AlreadyPresentDocumentResponse alreadyPresentDocumentResponse = new AlreadyPresentDocumentResponse()
            .setId(from.getId())
            .setTitle(from.getTitle())
            .setDescription(from.getDescription())
            .setTypeIds(this.mapDocumentTypeEntitiesToDocumentTypeIds(from.getTypes()))
            .setImportance(from.getImportance());
        if (from.getCatalog() != null) {
            alreadyPresentDocumentResponse.setCatalogId(from.getCatalog().getId());
        }
        return alreadyPresentDocumentResponse;
    }

    private Set<Long> mapDocumentTypeEntitiesToDocumentTypeIds(Set<DocumentTypeEntity> documentTypeEntities) {
        return documentTypeEntities.stream()
            .map(DocumentTypeEntity::getId)
            .collect(toSet());
    }
}
