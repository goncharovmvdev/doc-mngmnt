package doc.mngmnt.service.api.user;

import doc.mngmnt.dto.user.SaveUserDto;
import doc.mngmnt.dto.user.AlreadyPresentUserDto;

public interface UserService {

    SaveUserDto register(SaveUserDto saveUserDto);

    AlreadyPresentUserDto update(AlreadyPresentUserDto alreadyPresentUserDto);
}
