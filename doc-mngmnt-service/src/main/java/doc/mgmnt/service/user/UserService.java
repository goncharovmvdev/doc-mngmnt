package doc.mgmnt.service.user;

import doc.mngmnt.dto.user.SaveUserDto;
import doc.mngmnt.dto.user.UpdateUserDto;
import doc.mngmnt.entity.user.UserEntity;

public interface UserService {

    UserEntity register(SaveUserDto saveUserDto);

    UserEntity update(UpdateUserDto updateUserDto);
}
