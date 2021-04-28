package doc.mngmnt.service.impl.mapper.user;

import doc.mngmnt.dto.user.SaveUserDto;
import doc.mngmnt.entity.user.UserEntity;
import doc.mngmnt.service.api.mapper.Mapper;
import org.springframework.stereotype.Service;

@Service
public class SaveUserDtoUserEntityMapper implements Mapper<SaveUserDto, UserEntity> {

    @Override
    public UserEntity map(SaveUserDto from) {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(from.getPassword());
        userEntity.setUsername(from.getUsername());
        userEntity.setEmail(from.getEmail());
        userEntity.setPhoneNumber(from.getPhoneNumber());
        return userEntity;
    }
}
