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
        /* CREATE DOCUMENT, NOT CATALOG */
        final String googleDriveDocumentId = googleDriveFileService.createFolder(
            saveDocumentRequest.getTitle(),
            this.getGoogleDriveCatalogId(saveDocumentRequest.getCatalogId())
        );
        DocumentEntity documentEntityToSave = saveDocumentRequestDocumentEntityMapper.map(saveDocumentRequest);
        /* Set google drive id to this document manually */
        documentEntityToSave.setStorageDocumentId(googleDriveDocumentId);
        /* Save files, create entities and assign them to current document */
        this.uploadNewFilesAndAssignThemToDocumentEntity(
            saveDocumentRequest.getSaveFiles(),
            documentEntityToSave
        );
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


        /* Rename files */
        this.renameFiles(updateDocumentRequest.getId(), updateDocumentRequest.getRenameFilesMap());
        /* Delete(detach) files */
        this.detachFiles(updateDocumentRequest.getDeleteFileIds());

        DocumentEntity documentEntity = updateDocumentRequestDocumentEntityMapper.map(updateDocumentRequest);
        this.uploadNewFilesAndAssignThemToDocumentEntity(
            updateDocumentRequest.getUploadNewFiles(),
            documentStorageId
        );

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

    private void uploadNewFilesAndAssignThemToDocumentEntity(Set<MultipartFile> uploadNewFiles, DocumentEntity documentEntity) {
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
        documentEntity.setFiles(configuredFileEntities);
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