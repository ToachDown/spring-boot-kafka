package dto;

import domain.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonDto {

    private String username;
    private String password;
    private String email;
    private String role;
}
