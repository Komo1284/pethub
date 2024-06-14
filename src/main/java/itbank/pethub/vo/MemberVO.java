package itbank.pethub.vo;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Entity
@NoArgsConstructor
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
    private String newpw;
    private int role;
    private String provider;
    private String providerId;

    @Builder
    public MemberVO(String userid, String userpw, String email, int role, String provider, String providerId) {

        this.userid = userid;
        this.userpw = userpw;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;

    }
}
