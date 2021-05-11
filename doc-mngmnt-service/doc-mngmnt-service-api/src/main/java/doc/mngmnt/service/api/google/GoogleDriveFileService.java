package doc.mngmnt.service.api.google;

import com.google.api.services.drive.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GoogleDriveFileService {

    List<File> findAllFiles();

    /**
     * @param googleDriveParentCatalogId <strong>Google Drive</strong> parent catalog id. May be <code>null</code>
     * @return file's id
     */
    String createCatalog(String catalogName, String googleDriveParentCatalogId);

    /**
     * @return <code>Map<String, String></code>, containing google drive ids as keys and original file names av values.
     */
    Map<String, String> createDocument(String documentName, String googleDriveCatalogId, Set<MultipartFile> multipartFiles);

    /**
     * @return file's id
     */
    String uploadFile(MultipartFile file, String googleDriveFolderId);

    File findFileMetadataById(String googleDriveFileId);

    OutputStream getFileContentById(String googleDriveFileId);

    /**
     * @return google drive file metadata
     */
    File renameFile(String googleDriveFileId, String newName);

    /**
     * @return google drive file metadata
     */
    File moveFolder(String googleDriveFolderToMoveId, String googleDriveNewParentFolderId);
}
