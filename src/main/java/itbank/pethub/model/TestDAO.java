package itbank.pethub.model;

import itbank.pethub.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestDAO {

    @Select("select * from Member order by id desc")
    List<MemberVO> selectAll();
}
