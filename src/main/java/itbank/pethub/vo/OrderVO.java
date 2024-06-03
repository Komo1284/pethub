package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderVO {
    private int id, member_id, delivery_id, order_status;
    private Date order_date;

}
