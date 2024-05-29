package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberVO {

    private int id;
    private String name, address, email, phone;
    private String userid, userpw, newpw, nick;


}
