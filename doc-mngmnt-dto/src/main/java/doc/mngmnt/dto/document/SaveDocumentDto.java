package doc.mngmnt.dto.document;

import doc.mngmnt.dto.file.FileDto;
import doc.mngmnt.entity.document.DocumentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveDocumentDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private Set<FileDto> filesDtos;
    private Set<String> typeNames;
    private DocumentEntity.Importance importance;
    private Long catalogId;
    @NotEmpty
    private Set<Long> ownerIds;
}
