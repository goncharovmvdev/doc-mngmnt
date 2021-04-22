package doc.mgmnt.service.document;

import doc.mngmnt.dto.document.SaveDocumentDto;
import doc.mngmnt.dto.document.UpdateDocumentDto;

public interface DocumentService {

    SaveDocumentDto save(SaveDocumentDto saveDocumentDto);

    UpdateDocumentDto update(UpdateDocumentDto updateDocumentDto);

    void delete(Long id);

    SaveDocumentDto findById(Long id);
}
