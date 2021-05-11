package doc.mngmnt.dto.catalog.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SaveCatalogRequest {
    @NotBlank
    private String originalName;
    private Long parentCatalogId;
}
