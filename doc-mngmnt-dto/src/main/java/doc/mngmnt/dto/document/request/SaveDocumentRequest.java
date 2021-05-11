package doc.mngmnt.dto.document.request;

import doc.mngmnt.entity.document.Importance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SaveDocumentRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private Set<MultipartFile> saveFiles;
    private Set<Long> typeIds;
    private Importance importance;
    private Long catalogId;
    @NotEmpty
    private Set<Long> moderIds;
}
