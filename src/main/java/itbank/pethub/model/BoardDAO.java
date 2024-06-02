package itbank.pethub.model;

import itbank.pethub.vo.BoardVO;
import itbank.pethub.vo.ReplyVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardDAO {

    // 전체 게시판
    @Select("select * from Board_view order by id desc")
    List<BoardVO> selectAll();

    @Insert("insert into Board(title, contents, type, member_id) values(#{title}, #{contents}, #{type}, 1)")
    int addWrite(BoardVO input);

    @Select("select * from Board_view where id = #{id}")
    BoardVO selectOne(int id);

    @Update("update Board set title = #{title}, contents = #{contents}, type = #{type} where id = #{id}")
    int updateBoard(BoardVO input);

    @Delete("delete from Board where id = #{id}")
    int deleteBoard(int id);

    // 공지 사항
    @Select("select * from Board where type = 1 order by id desc")
    List<BoardVO> selectAllNotice();

    // 강아지
    @Select("select * from Board where type = 2 order by id desc")
    List<BoardVO> selectAllDogs();

    // 고양이
    @Select("select * from Board where type = 3 order by id desc")
    List<BoardVO> selectAllCats();

    // 새
    @Select("select * from Board where type = 4 order by id desc")
    List<BoardVO> selectAllBirds();

    // 기타
    @Select("select * from Board where type = 5 order by id desc")
    List<BoardVO> selectAllEtcs();

    // 조회수 증가
    @Update("update Board set v_count = v_count + 1 WHERE id = #{id}")
    int viewUp(int id);

    // 댓글
    @Select("select * from Reply_view where board_id = #{id} order by id desc")
    List<ReplyVO> getReplies(int id);

    // 댓글 작성
    @Insert("insert into Reply (board_id, member_id, contents) values (#{board_id}, 1, #{contents})")
    int addReply(ReplyVO input);

    // 댓글 삭제
    @Delete("delete from Reply where id = #{id}")
    int deleteReply(int id);

    // 댓글 수정
    @Update("update Reply set contents = #{contents} where id = #{id}")
    int updateReply(ReplyVO input);

    @Select("select * from Reply_view where id = #{id} ")
    int selectReply(int id);
}
