package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
public class CartVO {
    private int id, order_id, order_item, count;
    private int order_price,origin_price,discount_price;

}
