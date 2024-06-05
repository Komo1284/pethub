package itbank.pethub.model;

import itbank.pethub.vo.CouponVO;
import itbank.pethub.vo.Member_CouponVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CouponDAO {

    @Select("SELECT mc.*, c.code, c.discount, c.min_price, c.discount_limit " +
            "FROM member_coupon mc " +
            "JOIN coupon c ON mc.coupon_id = c.id " +
            "WHERE mc.member_id = #{member_id}")
    List<Member_CouponVO> selectCouponsByMemberId(int memberId);
}