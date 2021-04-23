package doc.mngmnt.repository.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
/*

@JdbcTest
@Sql(scripts = {
    */
/* init schema *//*

    "classpath:db.migration/V1__init.sql",
    */
/* Setup test data *//*

    "classpath:V2__set_data.sql"
})
*/
/* TODO: 23.04.2021 как указать на папку, в которой есть класс с @SpringBootApplication.
    (В doc.mngmnt.runner) DocumentManagementApplicationRunner - нужный класс, но она не распознается.
    java.lang.IllegalArgumentException: Invalid source 'classpath:/doc/mngmnt/repository/test/doc.mngmnt.runner'
    Как пофиксить?
    @JdbcTest и @Sql сделают тесты не такими костыльными *//*

@ContextConfiguration(locations = {"doc.mngmnt.runner"}, inheritLocations = false)
public class CatalogJdbcTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void assertJdbcTemplateNotNull() {
        assertNotNull(jdbcTemplate);
    }

    @Test
    public void testAlterNameOfCatalog() {
        final String newName = "newName";
        final Long id = 1L;
        jdbcTemplate.update("UPDATE catalog SET name = ? WHERE id=?", newName, id);
        String catalogName = jdbcTemplate
            .queryForObject("SELECT catalog.name FROM catalog WHERE id=" + id, String.class);
        assertEquals(newName, catalogName);
    }

    @Test
    public void testDetachFileFromCatalog() {
        final Long detachDocumentIdFromCatalog = 1L;
        final int hasDetached = jdbcTemplate.update("UPDATE document AS doc SET catalog_id=? WHERE doc.id=?", null, detachDocumentIdFromCatalog);
        assertEquals(1, hasDetached);
    }
}
*/
