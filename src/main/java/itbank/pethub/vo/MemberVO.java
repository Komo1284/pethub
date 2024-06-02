package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberVO {
    private int id;
    private String userid,userpw;
    private String name, nick, email, phoneNumber,address;

    private String createdAt, updatedAt;


}
