package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class OrderVO {
    private int id, member_id, delivery_id;
    private Date order_date;
    private int order_status;

}
