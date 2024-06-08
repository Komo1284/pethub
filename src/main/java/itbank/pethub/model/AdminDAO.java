package itbank.pethub.model;

import itbank.pethub.vo.CouponVO;
import itbank.pethub.vo.MemberVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AdminDAO {

    @Update("update member set role = 1 where userid = #{userid}")
    int insertAdmin(MemberVO input);

    @Select("select * from member where role = 1")
    List<MemberVO> findAllAdmins();

    @Update("update member set role = 0 where id = #{id}")
    int deleteAdmin(int id);

    @Select("select * from coupon")
    List<CouponVO> findAllCoupons();

    @Insert("insert into member_coupon (member_id, coupon_id) values (#{member_id}, #{id})")
    int issue_coupons(int member_id, int id);

    int del_coupon(int id);

    @Select("select * from member where role = 0")
    List<MemberVO> selectAllMember();
}
