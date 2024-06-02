package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class OrdersVO {
    private int id, count, member_id, delivery_id, orderitem_id;
    private Date order_date;
    private String status;
    private String payment_method;  // 결제 방법 필드 추가
    private String merchant_uid;    // 결제 고유번호 필드 추가

    // 기본 생성자
    public OrdersVO() {}

    // 필요한 생성자 추가
    public OrdersVO(int member_id, int orderitem_id, int delivery_id) {
        this.member_id = member_id;
        this.orderitem_id = orderitem_id;
        this.delivery_id = delivery_id;
    }

}
