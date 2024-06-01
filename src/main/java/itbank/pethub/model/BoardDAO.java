package itbank.pethub.model;

import itbank.pethub.vo.BoardVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardDAO {

    @Select("select * from Board order by id desc")
    List<BoardVO> selectAll();

    @Insert("insert into Board(title, contents, type, member_id) values(#{title}, #{contents}, #{type}, 1)")
    int addWrite(BoardVO input);

    @Select("select * from Board where id = #{id}")
    BoardVO selectOne(int id);

    @Update("update Board set title = #{title}, contents = #{contents}, type = #{type} where id = #{id}")
    int updateBoard(BoardVO input);

    @Delete("delete from Board where id = #{id}")
    int deleteBoard(int id);
}
