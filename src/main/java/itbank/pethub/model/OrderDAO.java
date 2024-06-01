package itbank.pethub.model;

import itbank.pethub.vo.CartVO;
import itbank.pethub.vo.OrderItemVO;
import itbank.pethub.vo.OrderVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderDAO {

    @Insert("INSERT INTO Cart (member_id, order_id, quantity, order_price) VALUES (#{member_id} #{order_id} #{quantity} #{order_price})")
    void insertCart(CartVO id);

}