package itbank.pethub.service;

import itbank.pethub.model.OrderDAO;
import itbank.pethub.vo.CartVO;
import itbank.pethub.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    @Autowired
    private OrderDAO od;

    public void addCart(CartVO order_item) {
        od.insertCart(order_item);
    }

    public OrderVO createOrder(int member_id, OrderVO order_item, String couponCode)
    {
        OrderVO order = new OrderVO();
        order.setMember_id(member_id);
       // order.setCreatedAt(LocalDateTime.now());

        BigDecimal totalPrice = BigDecimal.ZERO;

        return order;
    }
}
