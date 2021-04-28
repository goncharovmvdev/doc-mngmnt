package doc.mngmnt.repository.document;

import doc.mngmnt.entity.document.DocumentTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentTypeRepository extends JpaRepository<DocumentTypeEntity, Long> {

    DocumentTypeEntity findOneByName(String name);
}
