package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MemberVO {

    private int id, profile;
    private String name, address, email, phone;
    private String userid, userpw, newpw, nick;
    private String image;
    private MultipartFile upload;

}
