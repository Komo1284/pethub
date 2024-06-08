package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ReplyVO {
    private int id, member_id, board_id;
    private String contents, nick, profile;
    private Date w_date;
    private String board_title;
}
