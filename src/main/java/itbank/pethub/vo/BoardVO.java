package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class BoardVO {
    private int id, type, member_id;
    private String title, contents, profile, category; // categoryName 속성 추가
    private Date w_date;
    private boolean secret;
    private String nick;
    private int v_count;
}
