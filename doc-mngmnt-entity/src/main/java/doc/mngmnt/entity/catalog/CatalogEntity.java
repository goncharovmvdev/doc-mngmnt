package doc.mngmnt.entity.catalog;

import doc.mngmnt.entity.audit.PersistOpsAuthorRecordingEntity;
import doc.mngmnt.entity.document.DocumentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "\"catalog\""/* , schema = "public" */)
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

    // TODO: 18.04.2021 вообще бан
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "\"parent_child_catalog_tree\"",
        joinColumns = {@JoinColumn(name = "parent_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "child_id", referencedColumnName = "id")})
    private Set<CatalogEntity> parentCatalogs;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "\"parent_child_catalog_tree\"",
        joinColumns = {@JoinColumn(name = "child_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "parent_id", referencedColumnName = "id")})
    private Set<CatalogEntity> childCatalogs;

    //todo 4ek
    @OneToMany(mappedBy = "id", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<DocumentEntity> documents;
}
