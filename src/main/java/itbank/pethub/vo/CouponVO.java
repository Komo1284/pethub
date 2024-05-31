package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CouponVO {
    private int id, discount, price_limit, discount_limit;
    private String code;
    private Date req_date, end_date;

}
