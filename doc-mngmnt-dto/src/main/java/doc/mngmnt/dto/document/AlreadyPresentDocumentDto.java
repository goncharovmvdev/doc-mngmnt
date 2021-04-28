package doc.mngmnt.dto.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false, of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class AlreadyPresentDocumentDto extends SaveDocumentDto {
    @NotNull
    private Long id;
}
