package doc.mngmnt.service.impl.user;

import doc.mngmnt.dto.user.SaveUserDto;
import doc.mngmnt.dto.user.AlreadyPresentUserDto;
import doc.mngmnt.entity.user.UserEntity;
import doc.mngmnt.repository.user.UserRepository;
import doc.mngmnt.service.api.mapper.Mapper;
import doc.mngmnt.service.api.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Mapper<SaveUserDto, UserEntity> saveUserDtoUserEntityMapper;
    private final Mapper<AlreadyPresentUserDto, UserEntity> updateUserDtoUserEntityMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
        Mapper<SaveUserDto, UserEntity> saveUserDtoUserEntityMapper,
        Mapper<AlreadyPresentUserDto, UserEntity> updateUserDtoUserEntityMapper) {

        this.userRepository = userRepository;
        this.saveUserDtoUserEntityMapper = saveUserDtoUserEntityMapper;
        this.updateUserDtoUserEntityMapper = updateUserDtoUserEntityMapper;
    }

    @Override
    public SaveUserDto register(SaveUserDto saveUserDto) {
        final UserEntity userEntity = saveUserDtoUserEntityMapper.map(saveUserDto);
        userRepository.save(userEntity);
        return saveUserDto;
    }

    @Override
    public AlreadyPresentUserDto update(AlreadyPresentUserDto alreadyPresentUserDto) {
        final UserEntity userEntity = updateUserDtoUserEntityMapper.map(alreadyPresentUserDto);
        userRepository.save(userEntity);
        return alreadyPresentUserDto;
    }
}
