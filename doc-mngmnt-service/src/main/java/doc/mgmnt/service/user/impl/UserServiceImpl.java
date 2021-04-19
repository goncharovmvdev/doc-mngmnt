package doc.mgmnt.service.user.impl;

import doc.mgmnt.service.props.UserServiceProperties;
import doc.mgmnt.service.user.UserService;
import doc.mngmnt.dto.user.SaveUserDto;
import doc.mngmnt.dto.user.UpdateUserDto;
import doc.mngmnt.entity.security.RoleEntity;
import doc.mngmnt.entity.user.UserEntity;
import doc.mngmnt.repository.repository.security.RoleRepository;
import doc.mngmnt.repository.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserServiceProperties userServiceProperties;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserServiceProperties userServiceProperties, UserRepository userRepository,
        RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userServiceProperties = userServiceProperties;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // TODO: 19.04.2021 норм?
    @Override
    public UserEntity register(SaveUserDto saveUserDto) {
        if (!isValidForRegistering(saveUserDto.getEmail(), saveUserDto.getUsername())) {
            throw new IllegalArgumentException();
        }
        return userRepository.save(mapDtoToEntity(saveUserDto));
    }

    @Override
    public UserEntity update(UpdateUserDto updateUserDto) {
        UserEntity updatedUser = this.mapDtoToEntity(updateUserDto);
        updatedUser.setId(updatedUser.getId());
        return userRepository.save(updatedUser);
    }

    private boolean isValidForRegistering(String email, String username) {
        return userRepository.findOneByEmailOrUsername(email, username) == null;
    }

    // TODO: 19.04.2021 как это сделать умнее?
    private UserEntity mapDtoToEntity(SaveUserDto saveUserDto) {
        UserEntity userEntity = new UserEntity();

        userEntity.setPassword(passwordEncoder.encode(saveUserDto.getPassword()));
        userEntity.setUsername(saveUserDto.getUsername());
        userEntity.setAccountNonExpired(userServiceProperties.getAccountNonExpired());
        userEntity.setAccountNonLocked(userServiceProperties.getAccountNonLocked());
        userEntity.setCredentialsNonExpired(userServiceProperties.getCredentialsNonExpired());
        userEntity.setEnabled(userServiceProperties.getEnabled());
        Set<RoleEntity> defaultRoleEntities = userServiceProperties.getDefaultRoleNames().stream()
            .map(roleRepository::findOneByRole)
            .collect(toSet());
        userEntity.setRoles(defaultRoleEntities);

        return userEntity;
    }
}
