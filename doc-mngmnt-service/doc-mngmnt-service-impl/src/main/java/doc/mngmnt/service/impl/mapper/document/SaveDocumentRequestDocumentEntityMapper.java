package doc.mngmnt.service.impl.mapper.document;

import doc.mngmnt.dto.document.request.SaveDocumentRequest;
import doc.mngmnt.entity.catalog.CatalogEntity;
import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.entity.document.DocumentTypeEntity;
import doc.mngmnt.entity.file.FileEntity;
import doc.mngmnt.entity.user.UserEntity;
import doc.mngmnt.repository.catalog.CatalogRepository;
import doc.mngmnt.repository.document.DocumentTypeRepository;
import doc.mngmnt.repository.user.UserRepository;
import doc.mngmnt.service.api.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class SaveDocumentRequestDocumentEntityMapper implements Mapper<SaveDocumentRequest, DocumentEntity> {
    private final DocumentTypeRepository documentTypeRepository;
    private final CatalogRepository catalogRepository;
    private final UserRepository userRepository;

    @Override
    public DocumentEntity map(SaveDocumentRequest from) {
        Objects.requireNonNull(from, "SaveDocumentRequest shouldn't be null");
        return new DocumentEntity()
            .setTitle(from.getTitle())
            .setDescription(from.getDescription())
            .setTypes(this.mapTypeIdsToDocumentTypeEntities(from.getTypeIds()))
            .setImportance(from.getImportance())
            .setCatalog(this.mapCatalogIdToCatalogEntity(from.getCatalogId()))
            .setModerIds(this.mapModerUserIdsToUserEntities(from.getModerIds()));
    }

    private Set<DocumentTypeEntity> mapTypeIdsToDocumentTypeEntities(Set<Long> typeIds) {
        if (typeIds != null) {
            return typeIds.stream()
                .map(typeId -> documentTypeRepository.findById(typeId).orElseThrow())
                .collect(toSet());
        }
        return new HashSet<>();
    }

    private CatalogEntity mapCatalogIdToCatalogEntity(Long id) {
        if (id != null) {
            return catalogRepository.findById(id)
                /* Catalog id should either be specified correctly(it must exist),
                 * or shouldn't be specified at all */
                .orElseThrow(() -> new RuntimeException("Specified catalog id doesn't exist"));
        }
        return null;
    }

    private Set<UserEntity> mapModerUserIdsToUserEntities(Set<Long> ids) {
        Objects.requireNonNull(ids, "Any document must have at least one owner");
        return ids.stream()
            .map(id -> userRepository.findById(id).orElseThrow(() -> new RuntimeException("No user with specified id found")))
            .collect(toSet());
    }
}
