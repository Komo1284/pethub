package itbank.pethub.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MemberVO {
    private int id;
    private String name, address, email, phone;
    private String userid, userpw, newpw, nick;
    private String role;
    private int profile;

}
