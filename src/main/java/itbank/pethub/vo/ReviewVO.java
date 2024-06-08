package itbank.pethub.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewVO {
    private int id, item_id, member_id, rating;
    private String contents, pic;
    private Date w_date;
    private String nick, item;

}
