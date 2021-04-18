package doc.mngmnt.repository.repository.file;

import doc.mngmnt.entity.file.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

}
