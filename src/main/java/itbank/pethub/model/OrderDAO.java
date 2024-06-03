package itbank.pethub.model;

import itbank.pethub.vo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderDAO {
    @Select("SELECT id FROM delivery_status ORDER BY id DESC LIMIT 1")
    int getdelivery_status_id();

    @Insert("Insert into delivery(address, post, status_id) values (#{address}, #{post},1)")
    int makedelivery(DeliveryVO dsv);

    @Select("SELECT id FROM delivery ORDER BY id DESC LIMIT 1")
    int getdelivery_id();

    @Insert("INSERT INTO `order` (member_id, delivery_id, order_status) VALUES (#{member_id}, #{delivery_id}, 1)")
    int makeOrder(OrderVO ov);

    @Select("select * from item where id=#{productId}")
    ItemVO getItem(int productId);

    @Select("SELECT id FROM `order` ORDER BY id DESC LIMIT 1")
    int getorderid();

    @Insert("insert into cart (order_id, order_item, order_price, count, origin_price) values (#{order_id}, #{order_item}, #{order_price}, #{count}, #{origin_price})")
    int makecart(CartVO cv);

    @Update("UPDATE cart SET count = count + #{count} WHERE id = #{id} AND order_id IN (SELECT o.id FROM `order` o " +
            "WHERE o.order_status = (SELECT os.id FROM order_status os WHERE os.name = '주문 접수'))")
    int countup(CartVO cartVO);

    @Select("SELECT * FROM cart WHERE order_id IN (SELECT id FROM `order` WHERE member_id = #{memberId})")
    public List<CartVO> getCarts(int memberId);

    @Select("select * from item order by id desc")
    List<ItemVO> selectAll();

    @Select("select * from item where id = #{id}")
    ItemVO selectOne(int id);

    @Select("SELECT id FROM cart WHERE order_id IN (SELECT id FROM `order` WHERE member_id=#{memberId} and order_status in (select id from order_status where name ='주문 접수')) AND order_item=#{id}")
    @ResultType(Integer.class)
    Integer getExistingOrderId(@Param("memberId") int memberId, @Param("id") int id);

    @Select("select * from cart where id=#{id}")
    CartVO selectCart(int id);

    @Delete("Delete From cart where order_id=#{orderId}")
    int deleteCart(int orderId);

    @Select("select delivery_id from `order` where id=#{order_id}")
    int getDeli_id(int order_id);



    @Delete("DELETE from `order` where id=#{orderId}")
    int deleteOrder(int orderId);



    @Delete("DELETE from delivery where id=#{dId}")
    int deleteDelivery(int dId);


    @Select("select * from modc where member_id=#{memberId}")
    List<MODCVO> selectMODC(int memberId);
}