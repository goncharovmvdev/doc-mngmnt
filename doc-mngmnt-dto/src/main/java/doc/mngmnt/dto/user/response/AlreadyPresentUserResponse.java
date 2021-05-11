package doc.mngmnt.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AlreadyPresentUserResponse {
    @NotBlank
    private String username;
    @Email
    private String email;
    @NotBlank
    private String phoneNumber;
}
