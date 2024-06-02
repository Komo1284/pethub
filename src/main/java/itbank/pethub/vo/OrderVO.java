package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class OrderVO {

    private int id;
    private int memberId;
    private String status;
    private int totalPrice;
    private String createdAt;
    private List<OrderItemVO> orderItems;

}
