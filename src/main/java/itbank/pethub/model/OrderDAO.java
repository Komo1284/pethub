package itbank.pethub.model;

import itbank.pethub.vo.*;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface OrderDAO {

    List<CartVO> getCartItemsByMemberId(int memberId);

    void updateCartItem(int itemId, int count);

    void deleteCartItem(int itemId);

    List<CartVO> getItemsByIds(List<Integer> itemIds);

    MemberVO getMemberById(int memberId);

    void insertOrder(OrdersVO order);

    void updateOrderItemStatus(int itemId, String status);

    void clearCart(int memberId, List<Integer> itemIds);
}