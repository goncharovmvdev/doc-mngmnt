package doc.mgmnt.service.document.impl;

import doc.mgmnt.service.document.DocumentService;
import doc.mngmnt.dto.document.SaveDocumentDto;
import doc.mngmnt.dto.document.UpdateDocumentDto;
import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.entity.document.DocumentTypeEntity;
import doc.mngmnt.entity.user.UserEntity;
import doc.mngmnt.repository.repository.catalog.CatalogRepository;
import doc.mngmnt.repository.repository.document.DocumentRepository;
import doc.mngmnt.repository.repository.document.DocumentTypeRepository;
import doc.mngmnt.repository.repository.file.FileRepository;
import doc.mngmnt.repository.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final CatalogRepository catalogRepository;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository, DocumentTypeRepository documentTypeRepository,
        CatalogRepository catalogRepository, FileRepository fileRepository, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.catalogRepository = catalogRepository;
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SaveDocumentDto save(SaveDocumentDto saveDocumentDto) {
        documentRepository.save(this.mapSaveDocumentDtoToEntity(saveDocumentDto));
        // TODO: 19.04.2021 persist files?
        return saveDocumentDto;
    }

    @Override
    public UpdateDocumentDto update(UpdateDocumentDto updateDocumentDto) {
        return null;
    }

    @Override
    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

    @Override
    public SaveDocumentDto findById(Long id) {
        DocumentEntity documentEntity = documentRepository.findById(id).get();

        SaveDocumentDto saveDocumentDto = new SaveDocumentDto();
        saveDocumentDto.setTitle(documentEntity.getTitle());
        saveDocumentDto.setDescription(documentEntity.getDescription());
        // TODO: 19.04.2021
        saveDocumentDto.setFiles(new HashSet<>());
        saveDocumentDto.setCatalogId(documentEntity.getCatalog().getId());
        saveDocumentDto.setTypeNames(documentEntity.getTypes().stream()
            .map(DocumentTypeEntity::getName)
            .collect(Collectors.toSet()));


        return saveDocumentDto;
    }

    private DocumentEntity mapSaveDocumentDtoToEntity(SaveDocumentDto saveDocumentDto) {
        DocumentEntity documentToSave = new DocumentEntity();

        documentToSave.setTitle(saveDocumentDto.getTitle());
        documentToSave.setDescription(saveDocumentDto.getDescription());
        documentToSave.setImportance(saveDocumentDto.getImportance());
        catalogRepository.findById(saveDocumentDto.getCatalogId()).ifPresent(documentToSave::setCatalog);
        documentToSave.setTypes(this.getDocumentTypesByTheirNames(saveDocumentDto.getTypeNames()));
        documentToSave.setOwnerIds(this.getOwnersByTheirIds(saveDocumentDto.getOwnerIds()));
        saveDocumentDto.getFiles().stream()
        // TODO: 19.04.2021 тотальный непон.
            /* .map(file -> file.hufvhoiuewjrv[pl]erv\) */
        // TODO: 19.04.2021 persist files?

        .collect(Collectors.toSet());
        return documentToSave;
    }

    private Set<UserEntity> getOwnersByTheirIds(Set<Long> ownerIds) {
        return ownerIds.stream()
            .map(ownerId -> userRepository.findById(ownerId).get())
            .collect(Collectors.toSet());
    }

    private Set<DocumentTypeEntity> getDocumentTypesByTheirNames(Set<String> typeNames) {
        return typeNames.stream()
            .map(documentTypeRepository::findOneByName)
            .collect(Collectors.toSet());
    }
}
