package doc.mngmnt.dto.catalog.response;

import doc.mngmnt.dto.document.response.AlreadyPresentDocumentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AlreadyPresentCatalogResponse {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    private Long parentCatalogId;
    private Set<AlreadyPresentDocumentResponse> documents;
}
