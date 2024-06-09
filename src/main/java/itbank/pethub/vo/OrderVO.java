package itbank.pethub.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class OrderVO {
    private int id, member_id, delivery_id,order_status;
    private Date order_date;
}
