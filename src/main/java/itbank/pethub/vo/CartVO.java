package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartVO {
    private int id, cart_deperate_id, order_id, order_item, order_price, count, origin_price, discount_price, coupon_check;
    private String item_name;

    public CartVO(){};
}
