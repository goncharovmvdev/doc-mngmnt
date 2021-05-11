package doc.mngmnt.rest.user;

import doc.mngmnt.dto.user.request.SaveUserRequest;
import doc.mngmnt.dto.user.request.UpdateUserRequest;
import doc.mngmnt.dto.user.response.AlreadyPresentUserResponse;
import doc.mngmnt.service.api.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/")
@PreAuthorize("permitAll()")
@Slf4j
@RequiredArgsConstructor
public class UserManagementController {
    private final UserService userService;

    @PutMapping("register")
    public ResponseEntity<AlreadyPresentUserResponse> register(@RequestBody @Valid SaveUserRequest saveUserRequest) {
        final AlreadyPresentUserResponse registered = userService.register(saveUserRequest);
        log.info("Successfully saved user having username: {}", saveUserRequest.getUsername());
        return ResponseEntity.ok(registered);
    }

    @PostMapping("/update")
    public ResponseEntity<AlreadyPresentUserResponse> update(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        final AlreadyPresentUserResponse updated = userService.update(updateUserRequest);
        log.info("Successfully updated user having db id: {}", updateUserRequest.getId());
        return ResponseEntity.ok(updated);
    }
}
