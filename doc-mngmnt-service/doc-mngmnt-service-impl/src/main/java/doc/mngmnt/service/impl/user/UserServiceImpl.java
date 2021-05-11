package doc.mngmnt.service.impl.user;

import doc.mngmnt.dto.user.request.SaveUserRequest;
import doc.mngmnt.dto.user.request.UpdateUserRequest;
import doc.mngmnt.dto.user.response.AlreadyPresentUserResponse;
import doc.mngmnt.entity.security.RoleEntity;
import doc.mngmnt.entity.user.UserEntity;
import doc.mngmnt.repository.security.RoleRepository;
import doc.mngmnt.repository.user.UserRepository;
import doc.mngmnt.service.api.mapper.Mapper;
import doc.mngmnt.service.api.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Service
@Transactional
@PropertySource("classpath:user-service.properties")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    private final Mapper<SaveUserRequest, UserEntity> saveUserDtoUserEntityMapper;
    private final Mapper<UpdateUserRequest, UserEntity> alreadyPresentUserDtoUserEntityMapper;
    private final Mapper<UserEntity, AlreadyPresentUserResponse> userEntityAlreadyPresentUserResponseMapper;

    @Value("${default-role-names}")
    private Set<String> defaultRoleNames;

    private Set<RoleEntity> defaultRoles;

    /**
     * Init roles
     */
    @PostConstruct
    public void getDefaultRoles() {
        defaultRoles = defaultRoleNames.stream()
            .map(roleRepository::findOneByName)
            .collect(toSet());
    }

    @Override
    public AlreadyPresentUserResponse register(SaveUserRequest saveUserRequest) {
        final UserEntity userEntity = saveUserDtoUserEntityMapper.map(saveUserRequest);
        this.setFullyEnabled(userEntity);
        userEntity.setRoles(defaultRoles);
        final UserEntity savedUserEntity = userRepository.save(userEntity);
        return userEntityAlreadyPresentUserResponseMapper.map(savedUserEntity);
    }

    @Override
    public AlreadyPresentUserResponse update(UpdateUserRequest updateUserRequest) {
        UserEntity userEntity = alreadyPresentUserDtoUserEntityMapper.map(updateUserRequest);
        final UserEntity updatedUserEntity = userRepository.save(userEntity);
        return userEntityAlreadyPresentUserResponseMapper.map(updatedUserEntity);
    }

    private UserEntity setFullyEnabled(UserEntity userEntity) {
        return userEntity
            .setAccountNonExpired(true)
            .setAccountNonLocked(true)
            .setCredentialsNonExpired(true)
            .setEnabled(true);
    }
}
