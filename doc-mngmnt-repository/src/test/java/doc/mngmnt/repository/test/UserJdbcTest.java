package doc.mngmnt.repository.test;

import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.entity.document.DocumentTypeEntity;
import doc.mngmnt.entity.file.FileEntity;
import doc.mngmnt.entity.security.RoleEntity;
import doc.mngmnt.entity.user.UserEntity;
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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.test.annotation.Rollback;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@SpringBootTest(classes = {JdbcConfig.class})
public class UserJdbcTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // TODO: 22.04.2021 тотальный ужас. Зато фетчит роли, документы и (вроде как) файлы)))
    private static final ResultSetExtractor<Collection<UserEntity>> rse = new ResultSetExtractor<Collection<UserEntity>>() {
        @Override
        public Collection<UserEntity> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, UserEntity> usersById = new HashMap<>();
            Map<Long, DocumentEntity> documentsById = new HashMap<>();

            while (rs.next()) {
                /* IN \"user\" TABLE */
                Long userId = rs.getLong("id");
                String password = rs.getString("password");
                String username = rs.getString("username");
                boolean accountNonExpired = rs.getBoolean("account_non_expired");
                boolean accountNonLocked = rs.getBoolean("account_non_locked");
                boolean credentialsNonExpired = rs.getBoolean("credentials_non_expired");
                boolean enabled = rs.getBoolean("enabled");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");

                /* IN \"role\" TABLE */
                Long roleId = rs.getLong("ROLID");
                String roleName = rs.getString("ROLNAME");

                /* IN document TABLE */
                Long documentId = rs.getLong("DOCID");
                String documentTitle = rs.getString("DOCTITLE");
                String documentDescription = rs.getString("DOCDESCRIPTION");

                //IN file TABLE
                Long fileId = rs.getLong("FILID");
                String filePath = rs.getString("FILPATH");

                //IN document_type TABLE
                Long documentTypeId = rs.getLong("DOCTYPEID");
                String documentTypeName = rs.getString("DOCTYPENAME");

                UserEntity currentUser = usersById.get(userId);
                if (currentUser == null) {
                    /* CREATE NEW USER */
                    currentUser = new UserEntity();
                    currentUser.setId(userId);
                    currentUser.setPassword(password);
                    currentUser.setUsername(username);
                    currentUser.setAccountNonExpired(accountNonExpired);
                    currentUser.setAccountNonLocked(accountNonLocked);
                    currentUser.setCredentialsNonExpired(credentialsNonExpired);
                    currentUser.setEnabled(enabled);
                    currentUser.setEmail(email);
                    currentUser.setPhoneNumber(phoneNumber);
                    currentUser.setRoles(new HashSet<>());
                    currentUser.setDocuments(new HashSet<>());

                    usersById.put(currentUser.getId(), currentUser);
                }
                /* ADD ROLE ENTITY */
                RoleEntity roleEntity = new RoleEntity();
                roleEntity.setId(roleId);
                roleEntity.setName(roleName);
                currentUser.getRoles().add(roleEntity);

                /* ADD DOCUMENT ENTITIES */
                DocumentEntity currentDocument = documentsById.get(documentId);
                if (currentDocument == null) {
                    currentDocument = new DocumentEntity();
                    currentDocument.setId(documentId);
                    currentDocument.setTitle(documentTitle);
                    currentDocument.setDescription(documentDescription);
                    currentDocument.setFiles(new HashSet<>());
                    documentsById.put(currentDocument.getId(), currentDocument);
                }
                /* ADD FILE ENTITIES */
                FileEntity fileEntity = new FileEntity();
                fileEntity.setId(fileId);
                fileEntity.setPath(filePath);
                currentDocument.getFiles().add(fileEntity);

                /* ADD DOCUMENT TYPES ENTITIES */
                DocumentTypeEntity documentTypeEntity = new DocumentTypeEntity();
                documentTypeEntity.setId(documentTypeId);
                documentTypeEntity.setName(documentTypeName);

                currentUser.setDocuments(new HashSet<>(documentsById.values()));
            }
            return usersById.values();
        }
    };

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

    /**
     * 3 users are in db by default
     */
    @Test
    public void testGetAllUsers() {
        Collection<UserEntity> users = jdbcTemplate.query("SELECT *," +
            " ROL.id AS ROLID," +
            " ROL.name AS ROLNAME," +
            " DOC.id AS DOCID," +
            " DOC.title AS DOCTITLE," +
            " DOC.description AS DOCDESCRIPTION," +
            " DOC.importance AS DOCIMPORTANCE," +
            " FIL.id AS FILID," +
            " DOCTYPE.id AS DOCTYPEID," +
            " DOCTYPE.name AS DOCTYPENAME," +
            " FIL.path AS FILPATH" +
            " FROM \"user\" AS USR" +
            //GET ROLES
            " LEFT JOIN user_role USRROL ON USR.id = USRROL.user_id" +
            " LEFT JOIN \"role\" AS ROL ON USRROL.role_id = ROL.id" +
            //GET DOCUMENTS
            " LEFT JOIN user_document AS USRDOC ON USR.id = USRDOC.user_id" +
            //GET DOCUMENT TYPES
            " LEFT JOIN document DOC ON USRDOC.document_id = DOC.id" +
            " LEFT OUTER JOIN document_document_type DOCDOCTYPE ON DOC.id = DOCDOCTYPE.document_id" +
            " LEFT JOIN document_type DOCTYPE ON DOCDOCTYPE.document_type_id = DOCTYPE.id" +
            //GET FILES
            " LEFT JOIN file FIL ON DOC.id = FIL.document_id", rse);

        Objects.requireNonNull(users, "No users were found");
        users.forEach(System.out::println);
    }

    // TODO: 22.04.2021 без каскадов(
    @Test
    public void saveUser() {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement =
                con.prepareStatement("INSERT INTO \"user\"(password, username, email) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, "password");
            preparedStatement.setString(2, "tesUser");
            preparedStatement.setString(3, "testUser@digdes.com");
            return preparedStatement;
        }, keyHolder);
    }
}

