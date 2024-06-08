package itbank.pethub.model;

import itbank.pethub.vo.BoardVO;
import itbank.pethub.vo.ReplyVO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardDAO {
    // 자유 게시판
    @Select("<script>" +
            "SELECT * FROM board_view WHERE type = 5" +
            "<if test='group != null and search != null'> " +
            "and ${group} LIKE CONCAT('%', #{search}, '%') " +
            "</if> " +
            "ORDER BY id DESC " +
            "LIMIT #{offset}, #{boardCount}" +
            "</script>")
    List<BoardVO> selectAll(Map<String, Object> param);

    @Insert("insert into board(title, contents, type, member_id) values(#{title}, #{contents}, #{type}, #{member_id})")
    int addWrite(BoardVO input);

    @Select("select * from board_view where id = #{id}")
    BoardVO selectOne(int id);

    @Update("update board set title = #{title}, contents = #{contents}, type = #{type} where id = #{id}")
    int updateBoard(BoardVO input);

    @Delete("delete from board where id = #{id}")
    int deleteBoard(int id);

    // 공지 사항
    @Select("<script>" +
            "SELECT * FROM board_view WHERE type = 1" +
            "<if test='group != null and search != null'> " +
            "and ${group} LIKE CONCAT('%', #{search}, '%') " +
            "</if> " +
            "ORDER BY id DESC " +
            "LIMIT #{offset}, #{boardCount}" +
            "</script>")
    List<BoardVO> selectAllNotice(Map<String, Object> param);

    // 강아지
    @Select("<script>" +
            "SELECT * FROM board_view WHERE type = 6" +
            "<if test='group != null and search != null'> " +
            "and ${group} LIKE CONCAT('%', #{search}, '%') " +
            "</if> " +
            "ORDER BY id DESC " +
            "LIMIT #{offset}, #{boardCount}" +
            "</script>")
    List<BoardVO> selectAllDogs(Map<String, Object> param);

    // 고양이
    @Select("<script>" +
            "SELECT * FROM board_view WHERE type = 7" +
            "<if test='group != null and search != null'> " +
            "and ${group} LIKE CONCAT('%', #{search}, '%') " +
            "</if> " +
            "ORDER BY id DESC " +
            "LIMIT #{offset}, #{boardCount}" +
            "</script>")
    List<BoardVO> selectAllCats(Map<String, Object> param);

    // 새
    @Select("<script>" +
            "SELECT * FROM board_view WHERE type = 8" +
            "<if test='group != null and search != null'> " +
            "and ${group} LIKE CONCAT('%', #{search}, '%') " +
            "</if> " +
            "ORDER BY id DESC " +
            "LIMIT #{offset}, #{boardCount}" +
            "</script>")
    List<BoardVO> selectAllBirds(Map<String, Object> param);

    // 기타
    @Select("<script>" +
            "SELECT * FROM board_view WHERE type = 9" +
            "<if test='group != null and search != null'> " +
            "and ${group} LIKE CONCAT('%', #{search}, '%') " +
            "</if> " +
            "ORDER BY id DESC " +
            "LIMIT #{offset}, #{boardCount}" +
            "</script>")
    List<BoardVO> selectAllEtcs(Map<String, Object> param);

    // 조회수 증가
    @Update("update board set v_count = v_count + 1 WHERE id = #{id}")
    int viewUp(int id);

    // 댓글
    @Select("select * from reply_view where board_id = #{id} order by id desc")
    List<ReplyVO> getReplies(int id);

    // 댓글 작성
    @Insert("insert into reply (board_id, member_id, contents) values (#{board_id}, #{member_id}, #{contents})")
    int addReply(ReplyVO input);

    // 댓글 삭제
    @Delete("delete from reply where id = #{id}")
    int deleteReply(int id);

    // 댓글 수정
    @Update("update reply set contents = #{contents} where id = #{id}")
    int updateReply(ReplyVO input);

    @Select("select id, board_id, member_id, contents from reply_view where id = #{id}")
    ReplyVO selectReply(int id);

    // 게시판 페이징 및 검색 관련
    @Select("<script>" +
            "SELECT COUNT(*) FROM board_view where type = #{num} " +
            "<if test='group != null and search != null'> " +
            "and ${group} LIKE CONCAT('%', #{search}, '%') " +
            "</if>" +
            "</script>")
    int searchboard(Map<String, Object> param);

    @Select("SELECT COUNT(*) FROM board_view WHERE type = #{num}")
    int totalboard(int num);

    // 내가 쓴 게시판 및 댓글 관련 목록
    @Select("<script>" +
            "SELECT * FROM board_view WHERE member_id = #{member_id}" +
            "<if test='group != null and search != null'> " +
            "and ${group} LIKE CONCAT('%', #{search}, '%') " +
            "</if> " +
            "ORDER BY id DESC " +
            "LIMIT #{offset}, #{boardCount}" +
            "</script>")
    List<BoardVO> selectAllwroteBoard(Map<String, Object> param);

    @Select("<script>" +
            "SELECT * FROM reply_view WHERE member_id = #{member_id}" +
            "<if test='group != null and search != null'> " +
            "and ${group} LIKE CONCAT('%', #{search}, '%') " +
            "</if> " +
            "ORDER BY id DESC " +
            "LIMIT #{offset}, #{boardCount}" +
            "</script>")
    List<ReplyVO> selectAllwroteReply(Map<String, Object> param);

    // 내가 쓴 게시판 및 댓글 관련
    @Select("select count(*) from board_view where member_id = #{member_id}")
    int total(int member_id);

    @Select("select count(*) from reply_view where member_id = #{member_id}")
    int totalReply(int member_id);

    @Select("<script>" +
            "SELECT COUNT(*) FROM reply_view where member_id = #{member_id} " +
            "<if test='group != null and search != null'> " +
            "and ${group} LIKE CONCAT('%', #{search}, '%') " +
            "</if>" +
            "</script>")
    int searchReply(Map<String, Object> param);

    @Select("<script>" +
            "SELECT COUNT(*) FROM board_view where member_id = #{member_id} " +
            "<if test='group != null and search != null'> " +
            "and ${group} LIKE CONCAT('%', #{search}, '%') " +
            "</if>" +
            "</script>")
    int search(Map<String, Object> param);
}
