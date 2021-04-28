package doc.mngmnt.service.impl.catalog;

import doc.mngmnt.dto.catalog.AlreadyPresentCatalogDto;
import doc.mngmnt.entity.catalog.CatalogEntity;
import doc.mngmnt.repository.catalog.CatalogRepository;
import doc.mngmnt.service.api.catalog.CatalogService;
import doc.mngmnt.service.api.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {
    private final CatalogRepository catalogRepository;
    private final Mapper<CatalogEntity, AlreadyPresentCatalogDto> catalogEntityAlreadyPresentCatalogDtoMapper;

    @Autowired
    public CatalogServiceImpl(CatalogRepository catalogRepository,
        Mapper<CatalogEntity, AlreadyPresentCatalogDto> catalogEntityAlreadyPresentCatalogDtoMapper) {

        this.catalogRepository = catalogRepository;
        this.catalogEntityAlreadyPresentCatalogDtoMapper = catalogEntityAlreadyPresentCatalogDtoMapper;
    }

    @Override
    public CatalogEntity findById(Long id) {
        return catalogRepository.findById(id)
            .orElseThrow();
    }

    @Override
    public CatalogEntity findOneByName(String name) {
        return catalogRepository.findOneByName(name);
    }

    @Override
    public Set<AlreadyPresentCatalogDto> findChildCatalogsByParentId(Long parentId) {
        Set<AlreadyPresentCatalogDto> result = new HashSet<>();
        CatalogEntity parentCatalog = catalogRepository.findById(parentId)
            .orElseThrow();

        /* Fina all first-gen children */
        Set<CatalogEntity> children = catalogRepository.findAllByParentCatalog(parentCatalog);
        for (CatalogEntity child : children) {
            /* Add just found child */
            result.add(catalogEntityAlreadyPresentCatalogDtoMapper.map(child));
            /* While that child has its own children, search for them */
            // TODO: 28.04.2021 кста если потомков нет, нам вернется пустой сет или нал?
            while (!children.isEmpty()) {
                parentCatalog = child;
                children = catalogRepository.findAllByParentCatalog(parentCatalog);
            }
        }
        return result;
    }
}
