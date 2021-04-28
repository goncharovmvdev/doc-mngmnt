package doc.mngmnt.dto.catalog;

import doc.mngmnt.dto.document.AlreadyPresentDocumentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlreadyPresentCatalogDto {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    private Long parentCatalogId;
    private Set<AlreadyPresentDocumentDto> documents;
}
