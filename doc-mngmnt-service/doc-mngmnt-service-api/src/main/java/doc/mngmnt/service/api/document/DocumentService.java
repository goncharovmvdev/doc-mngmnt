package doc.mngmnt.service.api.document;

import doc.mngmnt.dto.document.SaveDocumentDto;
import doc.mngmnt.dto.document.AlreadyPresentDocumentDto;

public interface DocumentService {

    SaveDocumentDto save(SaveDocumentDto saveDocumentDto);

    AlreadyPresentDocumentDto update(AlreadyPresentDocumentDto alreadyPresentDocumentDto);

    AlreadyPresentDocumentDto findById(Long id);
}
