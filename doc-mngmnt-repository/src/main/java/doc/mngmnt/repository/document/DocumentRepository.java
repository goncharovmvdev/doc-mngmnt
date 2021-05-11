package doc.mngmnt.repository.document;

import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.entity.document.DocumentTypeEntity;
import doc.mngmnt.entity.document.Importance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {

    Set<DocumentEntity> findAllByTitleLikeIgnoreCase(String titleLike);

    Set<DocumentEntity> findAllByTypesIn(Set<DocumentTypeEntity> typeEntities);

    Set<DocumentEntity> findAllByImportance(Importance importance);
}
