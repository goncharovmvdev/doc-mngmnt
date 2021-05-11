package doc.mngmnt.service.api.user;

import doc.mngmnt.dto.user.request.SaveUserRequest;
import doc.mngmnt.dto.user.request.UpdateUserRequest;
import doc.mngmnt.dto.user.response.AlreadyPresentUserResponse;

public interface UserService {

    AlreadyPresentUserResponse register(SaveUserRequest saveUserRequest);

    AlreadyPresentUserResponse update(UpdateUserRequest updateUserRequest);
}
