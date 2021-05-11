package doc.mngmnt.repository.file;

import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.entity.file.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Set<FileEntity> findAllByStorageFileIdLikeIgnoreCase(String storageFileIdLike);

    Set<FileEntity> findAllByDocumentId(DocumentEntity documentEntity);
}
