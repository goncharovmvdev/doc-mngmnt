package doc.mngmnt.rest;

import doc.mngmnt.rest.catalog.CatalogManagementController;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class RestTest {
    private MockMvc mockMvc;
    @InjectMocks
    private CatalogManagementController catalogManagementController;

    // TODO: 10.05.2021 костыль. Как быть?
    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup()
            .build();
    }

    @Test
    @SneakyThrows
    void testGetCatalogByNameLikeIgnoreCase_nullNameLike() {
        mockMvc.perform(
            get("/catalog/find-by-name-like")
                .param("nameLike", (String) null))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @SneakyThrows
    void testGetCatalogByNameLikeIgnoreCase_() {
        mockMvc.perform(
            get("/catalog/find-by-name-like")
                .param("nameLike", "name"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
