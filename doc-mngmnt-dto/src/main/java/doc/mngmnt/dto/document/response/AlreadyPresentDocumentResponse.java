package doc.mngmnt.dto.document.response;

import doc.mngmnt.entity.document.Importance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AlreadyPresentDocumentResponse {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private Set<MultipartFile> files;
    private Set<Long> typeIds;
    private Importance importance;
    private Long catalogId;
}
