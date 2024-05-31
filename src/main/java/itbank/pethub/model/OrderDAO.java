package itbank.pethub.model;

import itbank.pethub.vo.OrderItemVO;
import itbank.pethub.vo.OrderVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderDAO {
    // Order 관련
    @Insert("INSERT INTO orders (user_id, total_price, created_at) VALUES (#{userId}, #{totalPrice}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertOrder(OrderVO order);

    // OrderItem 관련
    @Insert("INSERT INTO order_item (order_id, product_id, quantity, price) VALUES (#{orderId}, #{productId}, #{quantity}, #{price})")
    void insertOrderItem(OrderItemVO orderItem);

    // Coupon 관련
  //  @Select("SELECT * FROM coupon WHERE code = #{code}")
  //  CouponVO findCouponByCode(String code);

}