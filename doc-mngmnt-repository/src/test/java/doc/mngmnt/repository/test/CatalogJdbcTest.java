package doc.mngmnt.repository.test;

import doc.mngmnt.entity.catalog.CatalogEntity;
import doc.mngmnt.entity.user.UserEntity;
import doc.mngmnt.repository.test.config.JdbcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(classes = {JdbcConfig.class})
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
