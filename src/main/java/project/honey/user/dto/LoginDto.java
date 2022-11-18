package project.honey.user.dto;

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
