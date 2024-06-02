package itbank.pethub.service;

import itbank.pethub.model.OrderDAO;
import itbank.pethub.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDAO od;

    public List<CartVO> getCartItems(int memberId) {
        return od.getCartItemsByMemberId(memberId);
    }

    public void updateCartItem(int itemId, int count) {
        od.updateCartItem(itemId, count);
    }

    public void deleteCartItem(int itemId) {
        od.deleteCartItem(itemId);
    }

    public List<CartVO> getSelectedItems(List<Integer> itemIds) {
        return od.getItemsByIds(itemIds);
    }

    public MemberVO getMemberInfo(int memberId) {
        return od.getMemberById(memberId);
    }

    public void processPayment(String payMethod, List<Integer> itemIds, int memberId, String merchantUid) {
        // 결제 성공 후 주문 정보를 데이터베이스에 저장
        OrdersVO order = new OrdersVO();
        order.setMember_id(memberId);
        order.setStatus("PAID");
        order.setOrder_date(new Date(System.currentTimeMillis()));
        order.setPayment_method(payMethod);
        order.setMerchant_uid(merchantUid);

        od.insertOrder(order);

        for (int itemId : itemIds) {
            od.updateOrderItemStatus(itemId, "ORDERED");
        }

        od.clearCart(memberId, itemIds);
    }

    public int calculateTotalPrice(List<CartVO> cartItems) {
        return cartItems.stream().mapToInt(item -> item.getUnit_price() * item.getCount()).sum();
    }

}