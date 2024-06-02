package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class CartVO {

    private int id;
    private int memberId;
    private List<CartItemVO> cartItems;
    private int totalPrice;

}
