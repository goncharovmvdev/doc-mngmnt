package doc.mngmnt.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(of = {"username", "email"})
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserDto {
    @NotBlank
    private String password;
    @NotBlank
    private String username;
    @Email
    private String email;
    private String phoneNumber;
}
