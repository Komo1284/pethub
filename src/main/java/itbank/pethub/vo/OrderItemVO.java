package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemVO {
    private int id, order_id, price, count;
    private String product_name;

}
