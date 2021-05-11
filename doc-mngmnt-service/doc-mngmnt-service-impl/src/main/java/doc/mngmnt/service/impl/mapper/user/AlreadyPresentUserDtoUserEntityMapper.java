package doc.mngmnt.service.impl.mapper.user;

import doc.mngmnt.dto.user.request.UpdateUserRequest;
import doc.mngmnt.entity.user.UserEntity;
import doc.mngmnt.service.api.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlreadyPresentUserDtoUserEntityMapper implements Mapper<UpdateUserRequest, UserEntity> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity map(UpdateUserRequest from) {
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(from.getId());
        userEntity.setPassword(passwordEncoder.encode(from.getPassword()));
        userEntity.setUsername(from.getUsername());
        userEntity.setEmail(from.getEmail());
        userEntity.setPhoneNumber(from.getPhoneNumber());
        return userEntity;
    }
}
