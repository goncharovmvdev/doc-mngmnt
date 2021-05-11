package doc.mngmnt.dto.catalog.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCatalogRequest {
    @NotNull
    private Long id;
    private String newName;
    private Long newParentCatalogId;
    private Set<Long> newAllowedUsers;
    private Set<Long> deleteAllowedUsers;
}
