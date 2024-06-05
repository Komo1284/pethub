package itbank.pethub.vo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "member")
public class MemberVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String phone;
    private String address;
    private String userid;
    private String userpw;
    private String nick;
    private String profile;
    private int ad;
    private String newpw;
    private String role;
}
