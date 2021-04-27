package doc.mngmnt.repository.test;

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
