package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Member_CouponVO {
    private int id, member_id, coupon_id;
    private Date end_date, reg_date;

    private String code;
    private int discount, min_price, discount_limit;

}