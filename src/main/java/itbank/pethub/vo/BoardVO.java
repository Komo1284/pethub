package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Getter
@Setter
public class BoardVO {
    private int id, type, member_id;
    private String title, contents;
    private Date w_date;
    private boolean secret;
    private String nick, profile;
    private int v_count;

}
