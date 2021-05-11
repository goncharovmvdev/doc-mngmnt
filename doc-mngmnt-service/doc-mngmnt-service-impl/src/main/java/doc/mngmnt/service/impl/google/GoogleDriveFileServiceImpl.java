package doc.mngmnt.service.impl.google;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import doc.mngmnt.service.api.google.GoogleDriveFileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class GoogleDriveFileServiceImpl implements GoogleDriveFileService {
    private final Drive drive;

    @Override
    @SneakyThrows
    public List<File> findAllFiles() {
        return drive.files()
            .list()
            .setPageSize(10)
            .setFields("nextPageToken, files(id, name)")
            .execute()
            .getFiles();
    }

    @Override
    @SneakyThrows
    public String createCatalog(String catalogName, String parentCatalogId) {
        File fileMetadata = new File()
            .setName(catalogName)
            .setMimeType("application/vnd.google-apps.folder");
        if (parentCatalogId != null) {
            fileMetadata.setParents(Collections.singletonList(parentCatalogId));
        }
        final File savedCatalogMetadata = drive.files()
            .create(fileMetadata)
            .setFields("id, name")
            .execute();
        return savedCatalogMetadata.getId();
    }

    @Override
    public Map<String, String> createDocument(String documentName, String googleDriveCatalogId, Set<MultipartFile> multipartFiles) {
        Map<String, String> googleIdsToOriginalNames = new HashMap<>();
        /* Basically, just create new directory in google drive */
        final String justCreatedGoogleDriveDocumentId = this.createCatalog(documentName, googleDriveCatalogId);
        googleIdsToOriginalNames.put(justCreatedGoogleDriveDocumentId, documentName);
        for (MultipartFile multipartFile : multipartFiles) {
            final String justCreatedGoogleDriveFileId = this.uploadFile(multipartFile, justCreatedGoogleDriveDocumentId);
            googleIdsToOriginalNames.put(justCreatedGoogleDriveFileId, multipartFile.getOriginalFilename());
        }
        return googleIdsToOriginalNames;
    }

    @Override
    @SneakyThrows
    public String uploadFile(MultipartFile file, String googleDriveFolderId) {
        if (file != null) {
            File fileMetadata = new File()
                .setParents(Collections.singletonList(googleDriveFolderId))
                .setName(file.getOriginalFilename());
            File uploadFile = drive
                .files()
                .create(fileMetadata,
                    new InputStreamContent(
                        file.getContentType(),
                        new ByteArrayInputStream(file.getBytes())
                    )
                )
                .setFields("id")
                .execute();
            return uploadFile.getId();
        }
        return null;
    }

    @Override
    @SneakyThrows
    public File findFileMetadataById(String googleDriveFileId) {
        return drive.files()
            .get(googleDriveFileId)
            .execute();
    }

    @Override
    @SneakyThrows
    public OutputStream getFileContentById(String googleDriveFileId) {
        OutputStream outputStream = new ByteArrayOutputStream();
        drive.files().get(googleDriveFileId)
            .executeMediaAndDownloadTo(outputStream);
        return outputStream;
    }

    @Override
    @SneakyThrows
    public File renameFile(String googleDriveFileId, String newName) {
        File fileMetadata = drive.files()
            .get(googleDriveFileId)
            .execute()
            .setName(newName);
        return drive.files()
            .update(googleDriveFileId, fileMetadata)
            .setFields("title")
            .execute();
    }

    @Override
    @SneakyThrows
    public File moveFolder(String folderToMoveId, String newParentFolderId) {

        /* Retrieve the existing parents to remove */
        File fileMetadata = drive.files()
            .get(folderToMoveId)
            .setFields("parents")
            .execute();
        StringBuilder previousParents = new StringBuilder();
        for (String parent : fileMetadata.getParents()) {
            previousParents.append(parent);
            previousParents.append(',');
        }
        /* Move the file to the new folder */
        return drive.files()
            .update(folderToMoveId, null)
            .setAddParents(newParentFolderId)
            .setRemoveParents(previousParents.toString())
            .setFields("id, parents")
            .execute();
    }
}