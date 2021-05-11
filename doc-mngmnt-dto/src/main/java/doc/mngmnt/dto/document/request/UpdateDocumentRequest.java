package doc.mngmnt.dto.document.request;

import doc.mngmnt.entity.document.Importance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDocumentRequest {
    @NotNull
    private Long id;

    private String newTitle;

    private String newDescription;

    private Set<MultipartFile> uploadNewFiles;
    /**
     * keys: db file ids
     * values: new file names
     */
    private Map<Long, String> renameFilesMap;
    private Set<Long> deleteFileIds;

    private Set<Long> addTypeIds;
    private Set<Long> deleteTypeIds;

    private Importance importance;

    private Long newCatalogId;

    private Set<Long> addModerIds;
    private Set<Long> deleteModerIds;
}
