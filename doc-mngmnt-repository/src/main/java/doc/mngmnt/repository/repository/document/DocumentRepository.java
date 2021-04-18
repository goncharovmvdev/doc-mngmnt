package doc.mngmnt.repository.repository.document;

import doc.mngmnt.entity.document.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface DocumentRepository extends RevisionRepository<DocumentEntity, Long, Long>,
    JpaRepository<DocumentEntity, Long> {

    DocumentEntity findOneByTitle(String title);
}
