package doc.mngmnt.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"username", "email"})
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserRequest {
    @NotBlank
    private String password;
    @NotBlank
    private String username;
    @Email
    private String email;
    private String phoneNumber;
}
