package doc.mngmnt.repository.config.audit;

import doc.mngmnt.repository.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {
    @Autowired
    private UserRepository userRepository;

    //threadlocal
    // TODO: 13.04.2021 не уверен, что это хорошо
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication currentUserAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if (currentUserAuthentication != null) {
            String currentUserName = currentUserAuthentication.getName();
            Long currentUserId = userRepository.findOneByUsername(currentUserName).getId();
            return Optional.of(currentUserId);
        }
        return Optional.of(0L);
    }
}
