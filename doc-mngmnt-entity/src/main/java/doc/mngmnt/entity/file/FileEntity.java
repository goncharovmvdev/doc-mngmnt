package doc.mngmnt.entity.file;

import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.entity.document.DocumentVersionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "file"/* , schema = "public" */)
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "path", nullable = false)
    @NotNull
    private String path;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private DocumentEntity documentId;

    @ManyToOne
    @JoinColumn(name = "document_version_id")
    private DocumentVersionEntity documentVersionId;
}
