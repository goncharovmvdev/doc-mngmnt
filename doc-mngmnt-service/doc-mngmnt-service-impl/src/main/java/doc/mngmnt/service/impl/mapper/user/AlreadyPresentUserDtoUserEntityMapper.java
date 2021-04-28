package doc.mngmnt.service.impl.mapper.user;

import doc.mngmnt.dto.user.AlreadyPresentUserDto;
import doc.mngmnt.dto.user.SaveUserDto;
import doc.mngmnt.entity.user.UserEntity;
import doc.mngmnt.service.api.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlreadyPresentUserDtoUserEntityMapper implements Mapper<AlreadyPresentUserDto, UserEntity> {
    private final Mapper<SaveUserDto, UserEntity> saveUserDtoUserEntityMapper;

    @Autowired
    public AlreadyPresentUserDtoUserEntityMapper(Mapper<SaveUserDto, UserEntity> saveUserDtoUserEntityMapper) {
        this.saveUserDtoUserEntityMapper = saveUserDtoUserEntityMapper;
    }

    @Override
    public UserEntity map(AlreadyPresentUserDto from) {
        final UserEntity userEntity = saveUserDtoUserEntityMapper.map(from);
        userEntity.setId(from.getId());
        userEntity.setPassword(from.getPassword());
        return userEntity;
    }
}
