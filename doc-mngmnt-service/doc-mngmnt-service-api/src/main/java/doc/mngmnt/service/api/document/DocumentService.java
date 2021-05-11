package doc.mngmnt.service.api.document;

import doc.mngmnt.dto.document.request.SaveDocumentRequest;
import doc.mngmnt.dto.document.request.UpdateDocumentRequest;
import doc.mngmnt.dto.document.response.AlreadyPresentDocumentResponse;

import java.util.Set;

public interface DocumentService {

    AlreadyPresentDocumentResponse save(SaveDocumentRequest saveDocumentRequest);

    AlreadyPresentDocumentResponse update(UpdateDocumentRequest updateDocumentRequest);

    AlreadyPresentDocumentResponse findById(Long id);

    void deleteById(Long id);
}
