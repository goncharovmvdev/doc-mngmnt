package doc.mngmnt.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private String path;
    private File file;
    private Long documentId;
}
