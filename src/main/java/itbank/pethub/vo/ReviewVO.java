package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewVO {
    private int id,item_id,member_id,rating;
    private String text;

}
