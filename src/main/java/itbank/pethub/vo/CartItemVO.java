package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemVO {

    private int id;
    private int cartId;
    private int itemId;
    private String itemName;
    private int quantity;
    private int price;

}
