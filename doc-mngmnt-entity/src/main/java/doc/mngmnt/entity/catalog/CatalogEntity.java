package doc.mngmnt.entity.catalog;

import doc.mngmnt.entity.audit.PersistOpsAuthorRecordingEntity;
import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.entity.document.DocumentVersionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "catalog"/* , schema = "public" */)
@Data
@EqualsAndHashCode(callSuper = false, of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class CatalogEntity extends PersistOpsAuthorRecordingEntity<Long> {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private CatalogEntity parentCatalog;

    // TODO: 20.04.2021 наследование в бд
    @OneToMany(mappedBy = "id", cascade = {CascadeType.ALL})
    private Set<DocumentEntity> documents;

    @OneToMany(mappedBy = "id", cascade = {CascadeType.ALL})
    private Set<DocumentVersionEntity> versionDocuments;
}
