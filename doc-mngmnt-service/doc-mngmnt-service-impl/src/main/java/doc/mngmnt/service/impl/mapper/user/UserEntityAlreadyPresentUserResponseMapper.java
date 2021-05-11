package doc.mngmnt.service.impl.mapper.user;

import doc.mngmnt.dto.user.response.AlreadyPresentUserResponse;
import doc.mngmnt.entity.user.UserEntity;
import doc.mngmnt.service.api.mapper.Mapper;
import org.springframework.stereotype.Service;

@Service
public class UserEntityAlreadyPresentUserResponseMapper implements Mapper<UserEntity, AlreadyPresentUserResponse> {
    @Override
    public AlreadyPresentUserResponse map(UserEntity userEntity) {
        // TODO: 10.05.2021 add implementation
        return null;
    }
}
