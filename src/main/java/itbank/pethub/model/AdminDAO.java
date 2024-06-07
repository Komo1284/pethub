package itbank.pethub.model;

import itbank.pethub.vo.MemberVO;
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
}
