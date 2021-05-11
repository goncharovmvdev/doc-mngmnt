package doc.mngmnt.rest.document;

import doc.mngmnt.dto.document.request.SaveDocumentRequest;
import doc.mngmnt.dto.document.request.UpdateDocumentRequest;
import doc.mngmnt.dto.document.response.AlreadyPresentDocumentResponse;
import doc.mngmnt.service.api.document.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/document/")
@PreAuthorize("isFullyAuthenticated()")
@Slf4j
@RequiredArgsConstructor
public class DocumentManagementController {
    private final DocumentService documentService;

    @PostMapping("register")
    public ResponseEntity<AlreadyPresentDocumentResponse> save(@RequestBody @Valid SaveDocumentRequest saveDocumentRequest) {
        final AlreadyPresentDocumentResponse saved = documentService.save(saveDocumentRequest);
        log.info("Successfully saved document");
        return ResponseEntity.ok(saved);
    }

    @PostMapping("update")
    public ResponseEntity<AlreadyPresentDocumentResponse> update(@RequestBody @Valid UpdateDocumentRequest updateDocumentRequest) {
        final AlreadyPresentDocumentResponse updated = documentService.update(updateDocumentRequest);
        log.info("Successfully updated document having db id: {}", updateDocumentRequest.getId());
        return ResponseEntity.ok(updated);
    }

    @GetMapping("find-by-id/{id}")
    public ResponseEntity<AlreadyPresentDocumentResponse> findById(@PathVariable("id") Long id) {
        final AlreadyPresentDocumentResponse byId = documentService.findById(id);
        return ResponseEntity.ok(byId);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        documentService.deleteById(id);
        log.info("Successfully deleted document having db id: {}", id);
        return ResponseEntity.ok(id);
    }
}
