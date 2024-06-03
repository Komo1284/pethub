package itbank.pethub.model;

import itbank.pethub.vo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderDAO {
    @Select("SELECT id FROM Delivery_Status ORDER BY id DESC LIMIT 1")
    int getdelivery_status_id();

    @Insert("Insert into Delivery(address, post, status_id) values (#{address}, #{post},1)")
    int makedelivery(DeliveryVO dsv);

    @Select("SELECT id FROM Delivery ORDER BY id DESC LIMIT 1")
    int getdelivery_id();

    @Insert("INSERT INTO `Order` (member_id, delivery_id, order_status) VALUES (#{member_id}, #{delivery_id}, 1)")
    int makeOrder(OrderVO ov);

    @Select("select * from Item where id=#{productId}")
    ItemVO getItem(int productId);

    @Select("SELECT id FROM `Order` ORDER BY id DESC LIMIT 1")
    int getorderid();

    @Insert("insert into Cart (order_id, order_item, order_price, count, origin_price) values (#{order_id}, #{order_item}, #{order_price}, #{count}, #{origin_price})")
    int makecart(CartVO cv);

    @Update("UPDATE Cart SET count = count + #{count} WHERE id = #{id} AND order_id IN (SELECT o.id FROM `Order` o " +
            "WHERE o.order_status = (SELECT os.id FROM Order_Status os WHERE os.name = '주문 접수'))")
    int countup(CartVO cartVO);

    @Select("SELECT * FROM Cart WHERE order_id IN (SELECT id FROM `Order` WHERE member_id = #{memberId})")
    public List<CartVO> getCarts(int memberId);

    @Select("select * from Item order by id desc")
    List<ItemVO> selectAll();

    @Select("select * from Item where id = #{id}")
    ItemVO selectOne(int id);

    @Select("SELECT id FROM Cart WHERE order_id IN (SELECT id FROM `Order` WHERE member_id=#{memberId} and order_status in (select id from Order_Status where name ='주문 접수')) AND order_item=#{id}")
    @ResultType(Integer.class)
    Integer getExistingOrderId(@Param("memberId") int memberId, @Param("id") int id);

    @Select("select * from Cart where id=#{id}")
    CartVO selectCart(int id);

    @Delete("Delete From Cart where order_id=#{orderId}")
    int deleteCart(int orderId);

    @Select("select order_status from Order where id=#{orderId}")
    int getOrder_status_id(int orderId);

    @Select("select delivery_id from Order where order_status=#{osId}")
    int getDeli_id(int osId);

    @Select("select status_id from Delivery where id=#{dId}")
    int getDeli_st_id(int dId);

    @Delete("DELETE from Order whhere id=#{orderId}")
    int deleteOrder(int orderId);

    @Delete("DELETE from Order_Status where id=#{osId}")
    int deleteOrderStatus(int osId);

    @Delete("DELETE from Delivery where id=#{dId}")
    int deleteDelivery(int dId);


}