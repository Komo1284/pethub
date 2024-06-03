package itbank.pethub.vo;

import lombok.Data;

@Data
public class MemberVO {
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
}
