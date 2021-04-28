package doc.mngmnt.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false, of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class AlreadyPresentUserDto extends SaveUserDto {
    @NotNull
    private Long id;
}