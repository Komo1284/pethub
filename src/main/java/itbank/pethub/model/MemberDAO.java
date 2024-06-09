package itbank.pethub.model;

import itbank.pethub.dto.UserDTO;
import itbank.pethub.oauth2.OAuth2UserPrincipal;
import itbank.pethub.vo.CouponVO;
import itbank.pethub.vo.MemberVO;
import itbank.pethub.vo.RefreshVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberDAO {

    @Select("select * from member where userid = #{userid} and userpw = #{userpw}")
    MemberVO selectOneNoAddress(MemberVO input);

    @Select("select * from member_address_view where userid = #{userid} and userpw = #{userpw}")
    MemberVO selectOne(MemberVO input);

    @Insert("insert into member (name, email, userid, userpw, nick, phone) " +
            "values (#{name}, #{email}, #{userid}, #{userpw}, #{nick}, #{phone})")
    int insert(MemberVO input);

    @Update("update member set userpw = #{userpw}, email = #{email}, " +
            "phone = #{phone}, profile = #{profile} where id = #{id}")
    int update(MemberVO input);

    @Delete("delete from member where id = #{id}")
    void delete(MemberVO member);

    @Select("select userid from member where email = #{email}")
    String findId(MemberVO input);

    @Select("select * from member where userid = #{userid} and email = #{email}")
    MemberVO findPw(MemberVO input);

    @Update("update member set userpw = #{userpw} where id = #{id}")
    void newPw(MemberVO input);

    @Select("SELECT COUNT(*) FROM member WHERE userid = #{userid}")
    int countByUserId(String userid);

    @Select("select * from member_coupons_view where member_id = #{member_id}")
    List<CouponVO> couponFindbyId(int member_id);

    @Update("update address set zip_code = #{zip_code}, primary_address = #{primary_address}, address_details = #{address_details} " +
            "where member_id = #{id}")
    int addressUpdate(MemberVO input);

    @Update("update member set userpw = #{userpw}, email = #{email}, " +
            "phone = #{phone} where id = #{id}")
    int updateNoProfile(MemberVO user);

    @Insert("insert into address (member_id, zip_code, primary_address, address_details) values (#{id}, #{zip_code}, #{primary_address}, #{address_details})")
    int insertAddress(MemberVO input);

    @Update("update member set ad = 1 where userid = #{userid}")
    void insertAd(MemberVO input);


    @Insert("insert into SnsMember(email, name, nickname, access_token, userid, refresh_token) values (#{email}, #{name}, #{nickname}, #{access_token}, #{userid}, #{refresh_token})")
    int insertSns(UserDTO userDTO);

    @Select("select * from SnsMember where userid = #{userid}")
    UserDTO selectSnsUser(String userid);

    @Update("update SnsMember set access_token = #{access_token} where userid = #{userid} and refresh_token = #{refresh_token}")
    int updateSns(UserDTO ud);

    @Delete("delete from SnsMember where userid = #{userid}")
    int deleteSns(UserDTO ud);

    @Select("select * from member_address_view where userid = #{userid}")
    MemberVO findByUserid(String userid);

    @Select("select * from member_address_view where userid = #{userid}")
    Boolean existsByUserid(String userid);

    @Insert("insert into refresh (userid, refreshtoken, expiration) values(#{userid}, #{refresh}, #{expiration})")
    void insertRefresh(RefreshVO refreshVO);

    @Select("select * from refresh where refreshtoken = #{refresh}")
    Boolean existsByRefresh(String refresh);

    @Delete("delete from refresh where refreshtoken = #{refresh}")
    void deleteByRefresh(String refresh);

    @Select("select * from SnsMember where access_token = #{access_token} and refresh_token = #{refresh_token}")
    UserDTO selectSnsUserOne(UserDTO userDTO);
}
