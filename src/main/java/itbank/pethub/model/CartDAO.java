package itbank.pethub.model;

import itbank.pethub.vo.CartVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CartDAO {

    @Select("SELECT * FROM cart WHERE member_id = #{member_id}")
    List<CartVO> findCartItems(int member_id);

}
