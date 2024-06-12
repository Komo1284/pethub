package itbank.pethub.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class CouponVO {

    private int member_id, coupon_id, id, discount, minimum_price, discount_limit, min_price;
    private String member_name, user_id, nickname, email, coupon_code, code;
    private Date coupon_reg_date, coupon_end_date;
}
