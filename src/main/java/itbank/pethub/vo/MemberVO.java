package itbank.pethub.vo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "member")
public class MemberVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String phone;
    private int zip_code;
    private String primary_address, address_details;
    private String userid;
    private String userpw, pwCheck;
    private String nick;
    private String profile;
    private int ad;
    private String accessToken;
    private String newpw;
    private String role;
}
