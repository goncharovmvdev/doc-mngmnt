package doc.mngmnt.repository.config.provider;

import doc.mngmnt.entity.user.UserEntity;
import doc.mngmnt.repository.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class PersistOpsAuditorAware implements AuditorAware<UserEntity> {
    @Autowired
    private UserRepository userRepository;

    // TODO: 20.04.2021 (мне) сделать ThreadLocal переменную с автонтикацией.
    @Override
    public Optional<UserEntity> getCurrentAuditor() {
        Authentication currentUserAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if (currentUserAuthentication != null) {
            String currentUserName = currentUserAuthentication.getName();
            UserEntity currentUser = userRepository.findOneByUsername(currentUserName);
            return Optional.of(currentUser);
        }
        return Optional.empty();
    }
}
