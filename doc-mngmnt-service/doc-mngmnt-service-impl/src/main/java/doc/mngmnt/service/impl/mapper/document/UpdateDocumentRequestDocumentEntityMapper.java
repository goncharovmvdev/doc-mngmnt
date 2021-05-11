package doc.mngmnt.service.impl.mapper.document;

import doc.mngmnt.dto.document.request.UpdateDocumentRequest;
import doc.mngmnt.entity.catalog.CatalogEntity;
import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.entity.document.DocumentTypeEntity;
import doc.mngmnt.entity.user.UserEntity;
import doc.mngmnt.repository.catalog.CatalogRepository;
import doc.mngmnt.repository.document.DocumentRepository;
import doc.mngmnt.repository.document.DocumentTypeRepository;
import doc.mngmnt.repository.user.UserRepository;
import doc.mngmnt.service.api.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class UpdateDocumentRequestDocumentEntityMapper implements Mapper<UpdateDocumentRequest, DocumentEntity> {
    private final CatalogRepository catalogRepository;
    private final DocumentRepository documentRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final UserRepository userRepository;

    @Override
    public DocumentEntity map(UpdateDocumentRequest from) {
        DocumentEntity documentEntity = documentRepository.findById(from.getNewCatalogId())
            .orElseThrow();
        documentEntity.setTitle(from.getNewTitle());
        documentEntity.setDescription(from.getNewDescription());
        documentEntity.setTypes(
            this.manageTypes(
                documentEntity,
                from.getAddTypeIds(),
                from.getDeleteTypeIds()
            )
        );
        documentEntity.setImportance(from.getImportance());
        documentEntity.setCatalog(this.mapCatalogIdToCatalogEntity(from.getNewCatalogId()));
        documentEntity.setModerIds(
            this.manageModerIds(
                documentEntity,
                from.getDeleteModerIds(),
                from.getAddModerIds()
            )
        );
        return documentEntity;
    }

    private Set<UserEntity> manageModerIds(DocumentEntity documentEntity, Set<Long> addModerIds, Set<Long> deleteModerIds) {
        /* Remove duplicates */
        addModerIds.removeAll(deleteModerIds);
        deleteModerIds.removeAll(addModerIds);
        Set<UserEntity> presentModers = documentEntity.getModerIds();

        /* Add moders */
        final Set<UserEntity> modersToAdd = addModerIds.stream()
            .map(moderIdToAdd -> userRepository.findById(moderIdToAdd).orElseThrow())
            .collect(toSet());
        presentModers.addAll(modersToAdd);
        /* Delete moders */
        final Set<UserEntity> modersToDelete = deleteModerIds.stream()
            .map(moderIdToDelete -> userRepository.findById(moderIdToDelete).orElseThrow())
            .collect(toSet());
        presentModers.removeAll(modersToDelete);

        return presentModers;
    }

    private Set<DocumentTypeEntity> manageTypes(DocumentEntity documentEntity, Set<Long> addTypeIds, Set<Long> deleteTypeIds) {
        /* Remove duplicates */
        addTypeIds.removeAll(deleteTypeIds);
        deleteTypeIds.removeAll(addTypeIds);

        Set<DocumentTypeEntity> presentTypes = documentEntity.getTypes();
        /* Add types */
        Set<DocumentTypeEntity> typesToAdd = addTypeIds.stream()
            .map(addTypeId -> documentTypeRepository.findById(addTypeId).orElseThrow())
            .collect(toSet());
        presentTypes.addAll(typesToAdd);
        /* Delete types */
        Set<DocumentTypeEntity> typesToDelete = deleteTypeIds.stream()
        .map(deleteTypeId -> documentTypeRepository.findById(deleteTypeId).orElseThrow())
        .collect(toSet());
        presentTypes.removeAll(typesToDelete);

        return presentTypes;
    }

    private CatalogEntity mapCatalogIdToCatalogEntity(Long id) {
        if (id != null) {
            return catalogRepository.findById(id)
                .orElseThrow();
        }
        return null;
    }
}
