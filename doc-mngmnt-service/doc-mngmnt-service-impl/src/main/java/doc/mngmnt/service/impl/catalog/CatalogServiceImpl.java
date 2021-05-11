package doc.mngmnt.service.impl.catalog;

import doc.mngmnt.dto.catalog.request.SaveCatalogRequest;
import doc.mngmnt.dto.catalog.request.UpdateCatalogRequest;
import doc.mngmnt.dto.catalog.response.AlreadyPresentCatalogResponse;
import doc.mngmnt.entity.catalog.CatalogEntity;
import doc.mngmnt.entity.user.UserEntity;
import doc.mngmnt.repository.catalog.CatalogRepository;
import doc.mngmnt.repository.user.UserRepository;
import doc.mngmnt.service.api.catalog.CatalogService;
import doc.mngmnt.service.api.google.GoogleDriveFileService;
import doc.mngmnt.service.api.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
@Transactional
@PropertySource("classpath:document-service.properties")
// TODO: 08.05.2021 ???
@PreAuthorize("permitAll()")
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {
    private final CatalogRepository catalogRepository;
    private final GoogleDriveFileService googleDriveFileService;
    private final UserRepository userRepository;

    private final Mapper<SaveCatalogRequest, CatalogEntity> saveCatalogRequestCatalogEntityMapper;
    private final Mapper<CatalogEntity, AlreadyPresentCatalogResponse> catalogEntityAlreadyPresentCatalogResponseMapper;

    @Override
    public AlreadyPresentCatalogResponse save(SaveCatalogRequest saveCatalogRequest) {
        String googleDriveCatalogId = null;
        if (saveCatalogRequest.getParentCatalogId() != null) {
            googleDriveCatalogId = catalogRepository.findById(saveCatalogRequest.getParentCatalogId())
                .orElseThrow()
                .getStorageCatalogId();
        }
        final String justCreatedGoogleDriveCatalogId = googleDriveFileService.createCatalog(
            saveCatalogRequest.getOriginalName(),
            googleDriveCatalogId
        );
        CatalogEntity catalogEntityToSave = saveCatalogRequestCatalogEntityMapper.map(saveCatalogRequest);
        catalogEntityToSave.setStorageCatalogId(justCreatedGoogleDriveCatalogId);
        catalogRepository.save(catalogEntityToSave);
        return catalogEntityAlreadyPresentCatalogResponseMapper.map(catalogEntityToSave);
    }

    @Override
    public AlreadyPresentCatalogResponse findById(Long id) {
        Objects.requireNonNull(id, "Id shouldn't be null");
        CatalogEntity catalogEntity = catalogRepository.findById(id)
            .orElseThrow();
        return catalogEntityAlreadyPresentCatalogResponseMapper.map(catalogEntity);
    }

    @Override
    public Set<AlreadyPresentCatalogResponse> findAllByOriginalNameLikeIgnoreCase(String nameLike) {
        Objects.requireNonNull(nameLike, "Name shouldn't be null");
        return catalogRepository.findAllByOriginalNameLikeIgnoreCase(nameLike).stream()
            /* Fetch only catalogs allowed to current users */
            .filter(catalogEntity -> new HashSet<>(catalogEntity.getAllowedUsers())
                .contains(this.getCurrentUserEntityFromSecurityContext()))
            .map(catalogEntityAlreadyPresentCatalogResponseMapper::map)
            .collect(toSet());
    }

    @Override
    public Set<AlreadyPresentCatalogResponse> findAllChildrenByParentCatalogId(Long parentId) {
        Objects.requireNonNull(parentId, "Parent catalog id shouldn't be null");
        Set<AlreadyPresentCatalogResponse> result = new HashSet<>();
        CatalogEntity parentCatalog = catalogRepository.findById(parentId)
            .orElseThrow();
        /* Find all first-gen children */
        Set<CatalogEntity> children = catalogRepository.findAllByParentCatalog(parentCatalog);
        for (CatalogEntity child : children) {
            /* Add just found child iff it is allowed for current user */
            final Set<UserEntity> allowedUsersForChild = new HashSet<>(child.getAllowedUsers());
            if (allowedUsersForChild.contains(this.getCurrentUserEntityFromSecurityContext())) {
                result.add(catalogEntityAlreadyPresentCatalogResponseMapper.map(child));
            }
            /* While that child has its own children, search for them and repeat */
            while (!children.isEmpty()) {
                parentCatalog = child;
                children = catalogRepository.findAllByParentCatalog(parentCatalog);
            }
        }
        return result;
    }

    @Override
    public AlreadyPresentCatalogResponse updateById(UpdateCatalogRequest updateCatalogRequest) {
        CatalogEntity catalogEntity = catalogRepository.findById(updateCatalogRequest.getId())
            .orElseThrow();
        catalogEntity
            .setOriginalName(updateCatalogRequest.getNewName())
            .setParentCatalog(catalogRepository.findById(updateCatalogRequest.getNewParentCatalogId())
                .orElseThrow());
        this.manageAllowedUsers(
            catalogEntity,
            updateCatalogRequest.getNewAllowedUsers(),
            updateCatalogRequest.getDeleteAllowedUsers()
        );
        final CatalogEntity newParentCatalog = catalogRepository.findById(updateCatalogRequest.getNewParentCatalogId())
            .orElseThrow();
        final String googleDriveNewParentCatalogId = newParentCatalog.getStorageCatalogId();
        /* Move catalog in doodle drive */
        if (!googleDriveNewParentCatalogId.equals(catalogEntity.getParentCatalog().getOriginalName())) {
            googleDriveFileService.moveFolder(
                catalogEntity.getStorageCatalogId(),
                googleDriveNewParentCatalogId
            );
        }
        return catalogEntityAlreadyPresentCatalogResponseMapper.map(catalogEntity);
    }

    private UserEntity getCurrentUserEntityFromSecurityContext() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return userRepository.findOneByUsername(authentication.getName());
        }
        throw new RuntimeException("Non-authenticated user");
    }

    private void manageAllowedUsers(CatalogEntity catalogEntity, Set<Long> newAllowedUserIds, Set<Long> deleteAllowedUserIds) {
        /* Remove duplicates */
        newAllowedUserIds.removeAll(deleteAllowedUserIds);
        deleteAllowedUserIds.removeAll(newAllowedUserIds);
        /* Add new allowed users */
        final Set<UserEntity> newAllowedUserEntities = newAllowedUserIds.stream()
            .map(newAllowedUserId -> userRepository.findById(newAllowedUserId)
                .orElseThrow())
            .collect(toSet());
        catalogEntity.getAllowedUsers().addAll(newAllowedUserEntities);
        /* Delete allowed users */
        final Set<UserEntity> deleteAllowedUserEntities = deleteAllowedUserIds.stream()
            .map(newAllowedUserId -> userRepository.findById(newAllowedUserId)
                .orElseThrow())
            .collect(toSet());
        catalogEntity.getAllowedUsers().removeAll(deleteAllowedUserEntities);
    }
}
