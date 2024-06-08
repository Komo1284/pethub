package itbank.pethub.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberVO {
    private int id, role;
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
}
