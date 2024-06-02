package itbank.pethub.model;

import itbank.pethub.vo.*;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface OrderDAO {

    // Cart 관련 메소드
    CartVO findCartByMemberId(int memberId);

    List<CartItemVO> findCartItemsByCartId(int cartId);

    void addItemToCart(CartItemVO cartItem);

    void removeItemFromCart(int cartItemId);

    void updateItemQuantity(int cartItemId, int quantity);

    // Order 관련 메소드
    void createOrder(OrderVO order);

    void createOrderItem(OrderItemVO orderItem);

    void updateOrderStatus(int orderId, String status);

    // Member 관련 메소드
    MemberVO findMemberById(int memberId);

    // 주문 현황
    List<CartVO> getOrders(int id);
}