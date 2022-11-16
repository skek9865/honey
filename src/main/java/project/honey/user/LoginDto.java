package project.honey.user;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class LoginDto {

    private String userId;

    private String passwd;
}
