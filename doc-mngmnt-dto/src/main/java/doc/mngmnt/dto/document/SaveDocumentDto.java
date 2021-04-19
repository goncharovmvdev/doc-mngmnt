package doc.mngmnt.dto.document;

import doc.mngmnt.entity.document.DocumentEntity.Importance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveDocumentDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private Importance importance;
    private Long catalogId;
    private Set<String> typeNames;
    private Set<Long> ownerIds;
    // TODO: 19.04.2021 как передавать файлы через дто
    private Set<File> files;
}
