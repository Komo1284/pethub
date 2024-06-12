package itbank.pethub.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private int id;
    private String access_token;
    private String name;
    private String email;
    private String nickname;
    private String userid;
    private String role;
    private String phone;
    private String profile;

}
