package doc.mngmnt.service.impl.mapper.user;

import doc.mngmnt.dto.user.request.SaveUserRequest;
import doc.mngmnt.entity.user.UserEntity;
import doc.mngmnt.service.api.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SaveUserDtoUserEntityMapper implements Mapper<SaveUserRequest, UserEntity> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity map(SaveUserRequest from) {
        Objects.requireNonNull(from, "SaveUserDto cant't be null");
        return new UserEntity()
            .setPassword(passwordEncoder.encode(from.getPassword()))
            .setUsername(from.getUsername())
            .setEmail(from.getEmail())
            .setPhoneNumber(from.getPhoneNumber());
    }
}
