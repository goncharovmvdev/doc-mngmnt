package doc.mngmnt.rest.catalog;

import doc.mngmnt.dto.catalog.request.SaveCatalogRequest;
import doc.mngmnt.dto.catalog.request.UpdateCatalogRequest;
import doc.mngmnt.dto.catalog.response.AlreadyPresentCatalogResponse;
import doc.mngmnt.service.api.catalog.CatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/catalog/")
@PreAuthorize("isFullyAuthenticated()")
@Slf4j
@RequiredArgsConstructor
public class CatalogManagementController {
    private final CatalogService catalogService;

    @PostMapping("save")
    private ResponseEntity<AlreadyPresentCatalogResponse> save(@RequestBody @Valid SaveCatalogRequest saveCatalogRequest) {
        final AlreadyPresentCatalogResponse saved = catalogService.save(saveCatalogRequest);
        log.info("Successfully saved new catalog having original name {}", saveCatalogRequest.getOriginalName());
        return ResponseEntity.ok(saved);
    }

    @GetMapping("find-by-id/{id}")
    public ResponseEntity<AlreadyPresentCatalogResponse> findById(@PathVariable("id") Long id) {
        final AlreadyPresentCatalogResponse alreadyPresentCatalogResponseById = catalogService.findById(id);
        return ResponseEntity.ok(alreadyPresentCatalogResponseById);
    }

    @GetMapping("find-all-by-name-like-ignore-case")
    public ResponseEntity<Set<AlreadyPresentCatalogResponse>> findAllByNameLikeIgnoreCase(String nameLike) {
        final Set<AlreadyPresentCatalogResponse> allByNameLikeIgnoreCase = catalogService.findAllByOriginalNameLikeIgnoreCase(nameLike);
        return ResponseEntity.ok(allByNameLikeIgnoreCase);
    }

    @GetMapping("find-all-children-by-parent-catalog-id/{id}")
    public ResponseEntity<Set<AlreadyPresentCatalogResponse>> findAllChildrenByParentCatalogId(@PathVariable("id") Long id) {
        final Set<AlreadyPresentCatalogResponse> allChildrenByParentCatalogId = catalogService.findAllChildrenByParentCatalogId(id);
        return ResponseEntity.ok(allChildrenByParentCatalogId);
    }

    @PostMapping("update-by-id")
    public ResponseEntity<AlreadyPresentCatalogResponse> updateById(@RequestBody @Valid UpdateCatalogRequest updateCatalogRequest) {
        final AlreadyPresentCatalogResponse updated = catalogService.updateById(updateCatalogRequest);
        log.info("Successfully updated catalog having db id: {}", updateCatalogRequest.getId());
        return ResponseEntity.ok(updated);
    }
}
