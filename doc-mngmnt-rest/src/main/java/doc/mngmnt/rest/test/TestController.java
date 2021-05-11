package doc.mngmnt.rest.test;

import doc.mngmnt.dto.catalog.request.SaveCatalogRequest;
import doc.mngmnt.dto.catalog.response.AlreadyPresentCatalogResponse;
import doc.mngmnt.dto.document.request.SaveDocumentRequest;
import doc.mngmnt.dto.document.response.AlreadyPresentDocumentResponse;
import doc.mngmnt.dto.user.request.SaveUserRequest;
import doc.mngmnt.dto.user.response.AlreadyPresentUserResponse;
import doc.mngmnt.service.api.catalog.CatalogService;
import doc.mngmnt.service.api.document.DocumentService;
import doc.mngmnt.service.api.google.GoogleDriveFileService;
import doc.mngmnt.service.api.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/test/")
@Slf4j
@RequiredArgsConstructor
public class TestController {
    private final GoogleDriveFileService googleDriveFileService;
    private final UserService userService;
    private final DocumentService documentService;

    private final CatalogService catalogService;

    @PostMapping("create-folder")
    public ResponseEntity<String> tet() {
        googleDriveFileService.createFolder("catalog", null);
        return ResponseEntity.ok("");
    }

    @PostMapping("create-second")
    public ResponseEntity<String> tot() {
        final String catalogId = googleDriveFileService.createFolder("second-catalog", null);
        googleDriveFileService.createFolder("third-catalog", catalogId);
        return ResponseEntity.ok("");
    }

    @PostMapping("save-user")
    public ResponseEntity<AlreadyPresentUserResponse> saveUser(@ModelAttribute @Valid SaveUserRequest saveUserRequest) {
        final AlreadyPresentUserResponse register = userService.register(saveUserRequest);
        return ResponseEntity.ok(register);
    }

    @PostMapping("save-document")
    public ResponseEntity<AlreadyPresentDocumentResponse> tit(@ModelAttribute @Valid SaveDocumentRequest saveDocumentRequest) {
        final AlreadyPresentDocumentResponse save = documentService.save(saveDocumentRequest);
        return ResponseEntity.ok(save);
    }

    @PostMapping("create-catalog")
    public ResponseEntity<AlreadyPresentCatalogResponse> createCatalog(@ModelAttribute @Valid SaveCatalogRequest saveCatalogRequest) {
        log.info("QADS - IN");
        final AlreadyPresentCatalogResponse save = catalogService.save(saveCatalogRequest);
        return ResponseEntity.ok(save);
    }
}
