package doc.mngmnt.service.impl.catalog;

import doc.mngmnt.dto.catalog.response.AlreadyPresentCatalogResponse;
import doc.mngmnt.dto.catalog.request.SaveCatalogRequest;
import doc.mngmnt.entity.catalog.CatalogEntity;
import doc.mngmnt.repository.catalog.CatalogRepository;
import doc.mngmnt.service.api.mapper.Mapper;
import doc.mngmnt.service.api.util.CatalogUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.File;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
/*

class CatalogServiceImplTest {
    private static final String TEST_FILES_STORAGE_DIR = "classpath:test/files/";
    @Spy
    private CatalogRepository catalogRepository;
    @Spy
    private Mapper<SaveCatalogRequest, CatalogEntity> saveCatalogDtoCatalogEntityMapper;
    @Spy
    private Mapper<CatalogEntity, AlreadyPresentCatalogResponse> catalogEntityAlreadyPresentCatalogDtoMapper;
    @Spy
    private CatalogUtil catalogUtil;

    private CatalogServiceImpl catalogServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        catalogServiceImpl = new CatalogServiceImpl(
            catalogRepository,
            saveCatalogDtoCatalogEntityMapper,
            catalogEntityAlreadyPresentCatalogDtoMapper,
            catalogUtil
        );
        catalogServiceImpl.setStoragePath(TEST_FILES_STORAGE_DIR);
        File dir = new File(TEST_FILES_STORAGE_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    @Test
    void save() {
        Mockito.when(saveCatalogDtoCatalogEntityMapper.map(any()))
            .thenReturn(new CatalogEntity()
                .setId(1L));
        Mockito.when(catalogRepository.save(any()))
            .thenReturn(new CatalogEntity());
        Mockito.when(catalogEntityAlreadyPresentCatalogDtoMapper.map(any()))
            .thenReturn(new AlreadyPresentCatalogResponse()
                .setId(1L));

        AlreadyPresentCatalogResponse alreadyPresentCatalogResponse = catalogServiceImpl.save(new SaveCatalogRequest());

        Assertions.assertAll(
            () -> Assertions.assertEquals(1L, 1L)
        );
    }

    @Test
    void findOneById() {
        Mockito.when(catalogRepository.findById(any()))
            .thenReturn(Optional.of(new CatalogEntity()));
    }

    @Test
    void findAllByNameLikeIgnoreCase() {
    }

    @Test
    void findAllByParentCatalogId() {
    }

    @Test
    void updateById() {
    }
}*/
