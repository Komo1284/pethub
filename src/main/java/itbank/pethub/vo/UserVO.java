package itbank.pethub.vo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String userid;
    private String userpw;
    private String nick;
    private String email;
    private String phone;
    private int ad;
    private String profile;
    private String role;
    private String token;

    private String provider;
    private String providerId;


    @Builder
    public UserVO(String userid, String userpw, String email, String role, String provider, String providerId) {

        this.userid = userid;
        this.userpw = userpw;
        this.email = email;
        this.role = role;;
        this.provider = provider;
        this.providerId = providerId;

    }
}
