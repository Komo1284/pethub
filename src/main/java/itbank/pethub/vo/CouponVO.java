package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CouponVO {
    private int id, discount, min_price, discount_limit;
    private String code;

}