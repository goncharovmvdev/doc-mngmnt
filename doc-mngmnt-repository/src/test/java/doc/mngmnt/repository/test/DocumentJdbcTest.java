package doc.mngmnt.repository.test;

import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.entity.document.DocumentTypeEntity;
import doc.mngmnt.repository.test.config.JdbcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.annotation.Rollback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/*

@SpringBootTest(classes = {JdbcConfig.class})
public class DocumentJdbcTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final ResultSetExtractor<Collection<DocumentEntity>> rse = new ResultSetExtractor<Collection<DocumentEntity>>() {
        @Override
        public Collection<DocumentEntity> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, DocumentEntity> documentsById = new HashMap<>();

            while (rs.next()) {
                */
/* IN document TABLE *//*

                Long documentId = rs.getLong("DOCID");

                //IN document_type TABLE
                String documentTypeName = rs.getString("DOCTYPENAME");

                */
/* ADD DOCUMENT ENTITIES *//*

                DocumentEntity currentDocument = documentsById.get(documentId);
                if (currentDocument == null) {
                    currentDocument = new DocumentEntity();
                    currentDocument.setId(documentId);
                    currentDocument.setTypes(new HashSet<>());
                    documentsById.put(currentDocument.getId(), currentDocument);
                }

                */
/* ADD DOCUMENT TYPES ENTITIES *//*

                DocumentTypeEntity documentTypeEntity = new DocumentTypeEntity();
                documentTypeEntity.setName(documentTypeName);

                currentDocument.getTypes().add(documentTypeEntity);
            }
            return documentsById.values();
        }
    };

    @Before
    @Rollback(false)
    public void setupData() {
        //init schema
        Resource initSchema = new ClassPathResource("db.migration/V1__init.sql");
        DatabasePopulator initSchemaPopulator = new ResourceDatabasePopulator(initSchema);
        DatabasePopulatorUtils.execute(initSchemaPopulator, Objects.requireNonNull(jdbcTemplate.getDataSource()));
        //setup data
        Resource setupData = new ClassPathResource("db.migration/V1__init.sql");
        DatabasePopulator setupDataPopulator = new ResourceDatabasePopulator(initSchema);
        DatabasePopulatorUtils.execute(setupDataPopulator, jdbcTemplate.getDataSource());
    }

    @Test
    public void assertJdbcTemplateNotNull() {
        assertNotNull(jdbcTemplate);
    }

    */
/*

    @Test
    public void testDetDocumentTypes() {
        Collection<DocumentEntity> documents = jdbcTemplate.query("SELECT *," +
                " DOC.ID AS DOCID," +
                " DOCTYPE.name AS DOCTYPENAME" +
                " FROM document AS DOC" +
                " LEFT JOIN document_document_type DOCDOCTYPE ON DOC.id = DOCDOCTYPE.document_id" +
                " LEFT JOIN document_type DOCTYPE on DOCDOCTYPE.document_type_id = DOCTYPE.id",
            rse);
        Objects.requireNonNull(documents, "No documents found");
        documents.forEach(System.out::println);
    }
}
*/
