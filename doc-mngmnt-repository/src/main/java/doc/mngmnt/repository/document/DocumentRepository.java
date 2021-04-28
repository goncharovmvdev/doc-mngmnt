package doc.mngmnt.repository.document;

import doc.mngmnt.entity.document.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {

    DocumentEntity findOneByTitle(String title);

    Set<DocumentEntity> findAllByTitleLikeIgnoreCase(String titleLike);
}
