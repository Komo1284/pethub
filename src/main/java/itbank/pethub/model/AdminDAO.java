package itbank.pethub.model;

import itbank.pethub.vo.*;
import org.apache.ibatis.annotations.*;

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

    @Delete("delete from coupon where id = #{id}")
    int del_coupon(int id);

    @Select("select * from member where role = 0")
    List<MemberVO> selectAllMember();

    @Insert("insert into coupon (code, discount, min_price, discount_limit) values (#{code}, #{discount}, #{min_price}, #{discount_limit})")
    int insertCoupon(CouponVO input);

    @Select("select * from member where role = 0 and ad = 1")
    List<MemberVO> selectAllMemberAd();

    @Insert("insert into item (type, category, name, price, pic, detail) values (#{type}, #{category}, #{name}, #{price}, #{pic}, #{detail})")
    int AddProduct(ItemVO item);

    @Select("select * from modc where order_status = '${msg}'")
    List<MODCVO> selectOrder(String msg);
}
