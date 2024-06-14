package itbank.pethub.model;

import itbank.pethub.config.oauth.provider.OAuth2UserInfo;
import itbank.pethub.vo.LoginVO;
import itbank.pethub.vo.MemberVO;
import itbank.pethub.vo.UserVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserDAO {

    @Select("select * from user where userid = #{userid}")
    UserVO findByUserid(String userid);

    @Insert("insert into user (name, userid, userpw, nick, email, phone, provider, providerId) " +
            "values (#{name}, #{userid}, #{userpw}, #{nick}, #{email}, #{phone}, #{provider}, #{providerId})")
    void insertUser(UserVO userVO);

    @Insert("insert into user (userid, userpw, role) values (#{userid}, #{userpw}, #{role})")
    void joinUser(UserVO userVO);

    @Select("select * from user where provider = #{provider} and providerId = #{providerId}")
    Optional<UserVO> findByProviderAndProviderId(OAuth2UserInfo oAuth2UserInfo);

    @Select("select * from user")
    List<UserVO> findAll();

    @Update("update user set " +
                "userid = #{userid}, " +
                "userpw = #{userpw}, " +
                "email = #{email}, " +
                "phone = #{phone}, " +
                "provider = #{provider}, " +
                "providerId = #{providerId}, " +
                "nick = #{nickname}, " +
                "name = #{name}")
     void updateUser(UserVO user);
}
