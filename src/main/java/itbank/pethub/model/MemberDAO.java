package itbank.pethub.model;

import itbank.pethub.vo.CouponVO;
import itbank.pethub.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberDAO {

    @Select("select * from member where userid = #{userid} and userpw=#{userpw}")
    MemberVO selectOne(MemberVO input);

    @Select("select * from member_coupons_view where member_id = #{member_id}")
    List<CouponVO> couponFindbyId(int member_id);
}
