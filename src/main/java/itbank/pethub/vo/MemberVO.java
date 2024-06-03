package itbank.pethub.vo;

import lombok.Data;

@Data
public class MemberVO {
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
}
