package doc.mngmnt.service.impl.document;

import doc.mngmnt.dto.document.request.SaveDocumentRequest;
import doc.mngmnt.dto.document.request.UpdateDocumentRequest;
import doc.mngmnt.dto.document.response.AlreadyPresentDocumentResponse;
import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.entity.file.FileEntity;
import doc.mngmnt.repository.catalog.CatalogRepository;
import doc.mngmnt.repository.document.DocumentRepository;
import doc.mngmnt.repository.file.FileRepository;
import doc.mngmnt.repository.user.UserRepository;
import doc.mngmnt.service.api.document.DocumentService;
import doc.mngmnt.service.api.google.GoogleDriveFileService;
import doc.mngmnt.service.api.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final GoogleDriveFileService googleDriveFileService;
    private final CatalogRepository catalogRepository;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    private final Mapper<SaveDocumentRequest, DocumentEntity> saveDocumentRequestDocumentEntityMapper;
    private final Mapper<UpdateDocumentRequest, DocumentEntity> updateDocumentRequestDocumentEntityMapper;
    private final Mapper<DocumentEntity, AlreadyPresentDocumentResponse> documentEntityAlreadyPresentDocumentDtoMapper;

    @Override
    public AlreadyPresentDocumentResponse save(SaveDocumentRequest saveDocumentRequest) {
        final Map<String, String> googleIdsToOriginalFileNames = googleDriveFileService.createDocument(
            saveDocumentRequest.getTitle(),
            this.getGoogleDriveCatalogId(saveDocumentRequest.getCatalogId()),
            saveDocumentRequest.getSaveFiles()
        );
        DocumentEntity documentEntityToSave = saveDocumentRequestDocumentEntityMapper.map(saveDocumentRequest);
        /* Set google drive id to this document manually */
        documentEntityToSave.setStorageDocumentId(
            this.getGoogleDriveIdFromGoogleIdsToOriginalNames(
                googleIdsToOriginalFileNames,
                documentEntityToSave.getTitle()
            )
        );
        /* Set google drive id to each file manually */
        final DocumentEntity savedDocumentEntity = documentRepository.save(documentEntityToSave);
        return documentEntityAlreadyPresentDocumentDtoMapper.map(savedDocumentEntity);
    }

    @Override
    public AlreadyPresentDocumentResponse update(UpdateDocumentRequest updateDocumentRequest) {
        if (!this.canUserUpdateDocument(updateDocumentRequest.getId())) {
            throw new RuntimeException("User can't update document with id: " + updateDocumentRequest.getId() + " - not a moder of it");
        }
        final DocumentEntity documentStorageId = documentRepository.findById(updateDocumentRequest.getId())
            .orElseThrow();

        /* Upload files and get configured entities */
        final Set<FileEntity> fileEntities = this.uploadNewFileAndGetEntities(
            updateDocumentRequest.getUploadNewFiles(),
            documentStorageId
        );
        /* Rename files */
        this.renameFiles(updateDocumentRequest.getId(), updateDocumentRequest.getRenameFilesMap());
        /* Delete(detach) files */
        this.detachFiles(updateDocumentRequest.getDeleteFileIds());

        DocumentEntity documentEntity = updateDocumentRequestDocumentEntityMapper.map(updateDocumentRequest);
        /* Add configured file entities manually */
        for (FileEntity file : fileEntities) {
            documentEntity.addFile(file);
        }
        final DocumentEntity savedDocumentEntity = documentRepository.save(documentEntity);

        return documentEntityAlreadyPresentDocumentDtoMapper.map(savedDocumentEntity);
    }

    @Override
    public AlreadyPresentDocumentResponse findById(Long id) {
        DocumentEntity documentEntity = documentRepository.findById(id).orElseThrow();
        return documentEntityAlreadyPresentDocumentDtoMapper.map(documentEntity);
    }

    /**
     * To delete a document, we just need to set the <code>nonDeleted</code> flag on.
     */
    @Override
    public void deleteById(Long id) {
        final DocumentEntity documentEntityWithDeleteFlagOn = documentRepository.findById(id)
            .orElseThrow()
            .setNonDeleted(false);
        documentRepository.save(documentEntityWithDeleteFlagOn);
    }

    private String getGoogleDriveCatalogId(Long dbCatalogId) {
        if (dbCatalogId != null) {
            return catalogRepository.findById(dbCatalogId)
                .orElseThrow()
                .getStorageCatalogId();
        }
        return null;
    }

    /**
     * @return google drive id(key from googleIdsToOriginalNames) by original name(value from googleIdsToOriginalNames)
     */
    private String getGoogleDriveIdFromGoogleIdsToOriginalNames(Map<String, String> googleIdsToOriginalNames, String originalName) {
        return googleIdsToOriginalNames.entrySet().stream()
            .filter(entry -> entry.getValue().equals(originalName))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElseThrow();
    }

    private Set<FileEntity> uploadNewFileAndGetEntities(Set<MultipartFile> uploadNewFiles, DocumentEntity documentEntity) {
        Set<FileEntity> configuredFileEntities = new HashSet<>();
        if (uploadNewFiles != null) {
            for (MultipartFile multipartFile : uploadNewFiles) {
                final String uploadFileId = googleDriveFileService.uploadFile(multipartFile, documentEntity.getStorageDocumentId());
                configuredFileEntities.add(
                    new FileEntity()
                        .setStorageFileId(uploadFileId)
                        .setOriginalName(multipartFile.getOriginalFilename())
                        .setDocumentId(documentEntity)
                );
            }
        }
        return configuredFileEntities;
    }

    private void detachFiles(Set<Long> deleteFileIds) {
        if (deleteFileIds != null) {
            for (Long deleteFileId : deleteFileIds) {
                fileRepository.findById(deleteFileId)
                    .orElseThrow()
                    /* Detach file */
                    .setDocumentId(null);
            }
        }
    }

    private boolean canUserUpdateDocument(Long documentId) {
        final DocumentEntity documentEntity = documentRepository.findById(documentId)
            .orElseThrow();
        /* Avoid lazy initialization exception */
        return new HashSet<>(documentEntity.getModerIds()).contains(
            userRepository.findOneByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
            )
        );
    }

    private void renameFiles(Long documentId, Map<Long, String> map) {
        DocumentEntity documentEntity = documentRepository.findById(documentId)
            .orElseThrow();
        /* Avoid lazy initialization exception */
        Set<FileEntity> fileEntitiesToRename = new HashSet<>(documentEntity.getFiles());
        map.forEach((key, value) -> {
            FileEntity fileToRename = fileEntitiesToRename.stream()
                .filter(fileEntity -> fileEntity.getId().equals(key))
                .findFirst()
                .orElseThrow();
            fileToRename.setOriginalName(value);
        });
    }
}